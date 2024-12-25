package com.example.bookdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuotesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quotes_list)

        val addButton: Button = findViewById(R.id.add_quote)

        val quotesList: RecyclerView = findViewById(R.id.recycler_view)
        val db = DBFormer(this, null)
        val quotes = db.getQuotes()
        val dividerItemDecoration = DividerItemDecoration(quotesList.context, DividerItemDecoration.VERTICAL)
        quotesList.addItemDecoration(dividerItemDecoration)

        quotesList.layoutManager = LinearLayoutManager(this)
        quotesList.adapter = QuoteAdapter(quotes, this)

        addButton.setOnClickListener {
            val intent = Intent(this, QuoteFormActivity::class.java)
            startActivity(intent)
        }

    }
}
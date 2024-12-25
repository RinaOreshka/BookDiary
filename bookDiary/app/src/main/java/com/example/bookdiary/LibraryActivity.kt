package com.example.bookdiary

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LibraryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_library)

        val searchField: EditText = findViewById(R.id.search_bar)
        val searchButton: ImageButton = findViewById(R.id.image_button)

        val booksList: RecyclerView = findViewById(R.id.recycler_view)
        val db = DBFormer(this, null)
        val books = db.getBooks()
        val dividerItemDecoration = DividerItemDecoration(booksList.context, DividerItemDecoration.VERTICAL)
        booksList.addItemDecoration(dividerItemDecoration)

        booksList.layoutManager = LinearLayoutManager(this)
        booksList.adapter = BookAdapter(books, this)

        searchButton.setOnClickListener{
            val name = searchField.text.toString()
            val db = DBFormer(this, null)
            val book = db.searchBook(name)
            booksList.layoutManager = LinearLayoutManager(this)
            booksList.adapter = BookAdapter(book, this)
        }

    }
}
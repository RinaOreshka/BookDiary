package com.example.bookdiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class QuoteFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quote_form)

        val text: EditText = findViewById(R.id.quote_text)
        val book: EditText = findViewById(R.id.book_name)
        val author:EditText = findViewById(R.id.author)
        val save:Button = findViewById(R.id.save_button)

        fun areFieldsEmpty(vararg fields: String): Boolean {
            return fields.any { it.isEmpty() }
        }

        save.setOnClickListener {
            val quoteText = text.text.toString()
            val quoteBook = book.text.toString()
            val quoteAuthor = author.text.toString()
            val intent = Intent(this, BookMainActivity::class.java)

            val db = DBFormer(this, null)
            val sharedPreferences = this.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            val user = sharedPreferences.getString("userId", null)

            if(areFieldsEmpty(quoteText, quoteBook, quoteAuthor))
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
            else {
                db.addQuote(quoteText, quoteBook, quoteAuthor, user)
                Toast.makeText(this, "Цитата сохранена!", Toast.LENGTH_LONG).show()
                startActivity(intent)
                author.text.clear()
                text.text.clear()
                book.text.clear()
            }
        }
    }
}
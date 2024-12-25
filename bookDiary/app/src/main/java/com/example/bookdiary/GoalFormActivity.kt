package com.example.bookdiary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class GoalFormActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goal_form)

        val startDate: EditText = findViewById(R.id.start_date)
        val endDate: EditText = findViewById(R.id.end_date)
        val pageSum: EditText = findViewById(R.id.page_sum)
        val bookName: EditText = findViewById(R.id.book_title)
        val saveButton: Button = findViewById(R.id.save_button)

        fun areFieldsEmpty(vararg fields: String): Boolean {
            return fields.any { it.isEmpty() }
        }

        saveButton.setOnClickListener {
            val start = startDate.text.toString()
            val end = endDate.text.toString()
            val pages = pageSum.text.toString()
            val book = bookName.text.toString()
            val intent = Intent(this, BookMainActivity::class.java)

            val db = DBFormer(this, null)
            val page: Int = pages.toInt()
            if(areFieldsEmpty(start, end, pages, book))
                    Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
            else {
                val result = db.addGoal(start, end, page, book)

                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                startDate.text.clear()
                endDate.text.clear()
                pageSum.text.clear()
                bookName.text.clear()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }
    }
}
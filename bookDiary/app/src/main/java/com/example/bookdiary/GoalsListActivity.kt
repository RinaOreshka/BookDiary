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

class GoalsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goals_list)
        val addButton: Button = findViewById(R.id.add_goal)

        val booksList: RecyclerView = findViewById(R.id.recycler_view)
        val db = DBFormer(this, null)
        val goals = db.getGoals()
        val dividerItemDecoration = DividerItemDecoration(booksList.context, DividerItemDecoration.VERTICAL)
        booksList.addItemDecoration(dividerItemDecoration)

        booksList.layoutManager = LinearLayoutManager(this)
        booksList.adapter = GoalAdapter(goals, this)

        addButton.setOnClickListener {
            val intent = Intent(this, GoalFormActivity::class.java)
            startActivity(intent)
        }
    }
}
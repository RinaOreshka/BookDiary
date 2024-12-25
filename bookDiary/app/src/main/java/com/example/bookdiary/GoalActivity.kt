package com.example.bookdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goal)

        val startDate: TextView = findViewById(R.id.start_date)
        val endDate: TextView = findViewById(R.id.end_date)
        val bookIsRead: TextView = findViewById(R.id.book_title)
        val pagesRead: EditText = findViewById(R.id.page_sum)
        val saveProgress: Button = findViewById(R.id.save_progress_button)
        val check: CheckBox = findViewById(R.id.read_checkbox)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)


        val goalId = intent.getStringExtra("goal_id")
        val db = DBFormer(this, null)
        val goal = db.getGoal(goalId)
        startDate.text = goal.startDate
        endDate.text = goal.endDate
        bookIsRead.text = db.getBookName(goal.book)
        val lastPage = db.getProgress(goalId)
        pagesRead.hint = "Прочитано: " + lastPage.toString() + ", измените число"
        saveProgress.setOnClickListener {
            val pageNum = pagesRead.text.toString()
            if (pageNum == ""){
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
            }
            else {
                db.changeProgress(goalId, pageNum)
            }
            if (check.isActivated){
                val intent = Intent(this, BookMainActivity::class.java)

                db.finishGoal(goalId)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        var progress = 0
        if (lastPage != 0){
            progress = (lastPage * 100)/goal.pageSumm
        }
        progressBar.progress = progress
    }
}
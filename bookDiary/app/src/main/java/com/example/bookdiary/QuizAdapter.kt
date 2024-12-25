package com.example.bookdiary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter (var quizz: List<Quiz>, var context: Context) : RecyclerView.Adapter<QuizAdapter.MyViewHolder>(){
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val text: TextView = view.findViewById(R.id.goal_text)
        val button: Button = view.findViewById(R.id.check_goal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.goal_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizz.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.text.text =quizz[position].name
        holder.button.setOnClickListener {
            val intent = Intent(context, QuizActActivity::class.java)
            intent.putExtra("quiz_id", quizz[position].id)
            context.startActivity(intent)
        }
    }
}
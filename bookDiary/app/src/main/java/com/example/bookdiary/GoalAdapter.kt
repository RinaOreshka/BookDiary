package com.example.bookdiary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalAdapter (var goals: List<Goal>, var context: Context) : RecyclerView.Adapter<GoalAdapter.MyViewHolder>(){
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val text: TextView = view.findViewById(R.id.goal_text)
        val button: Button = view.findViewById(R.id.check_goal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.goal_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return goals.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val db = DBFormer(context, null)
        val bookName = db.getBookName(goals[position].book)
        holder.text.text =bookName
        holder.button.setOnClickListener {
            val intent = Intent(context, GoalActivity::class.java)
            intent.putExtra("goal_id", goals[position].goalId)
            context.startActivity(intent)
        }
    }
}
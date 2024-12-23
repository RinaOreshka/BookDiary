package com.example.bookdiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter (var books: List<Book>, var context: Context) : RecyclerView.Adapter<BookAdapter.MyViewHolder>(){
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.book_title)
        val author: TextView = view.findViewById(R.id.book_author)
        val description: TextView = view.findViewById(R.id.book_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return books.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = books[position].name
        holder.author.text = books[position].author
        holder.description.text = books[position].description
    }
}
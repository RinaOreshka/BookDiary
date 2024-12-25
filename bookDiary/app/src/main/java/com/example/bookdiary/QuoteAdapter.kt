package com.example.bookdiary


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteAdapter (var quotes: List<Quote>, var context: Context) : RecyclerView.Adapter<QuoteAdapter.MyViewHolder>(){
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val text: TextView = view.findViewById(R.id.quote_text)
        val book: TextView = view.findViewById(R.id.book_title)
        val author: TextView = view.findViewById(R.id.book_author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.text.text = quotes[position].text
        holder.book.text = quotes[position].book
        holder.author.text = quotes[position].author
    }
}
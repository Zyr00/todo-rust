package com.example.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.FragmentHandlerActivity
import com.example.android.R
import com.example.android.adapters.ListAdapter.*
import com.example.android.classes.Todo

class ListAdapter(_list: MutableList<Todo>): RecyclerView.Adapter<ViewHolder>() {
    private val list = _list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        var desc = list[position].desc
        if (desc.length > 43) desc = "${list[position].desc.substring(0, 40)}..."
        holder.desc.text = desc
    }

    override fun getItemCount(): Int {
        return list.size
    }

    open inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.card_title)
        var desc: TextView = itemView.findViewById(R.id.card_desc)

        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Todo.toIntent(
                    list[adapterPosition],
                    FragmentHandlerActivity.TYPE_DETAILS,
                    context,
                    FragmentHandlerActivity::class.java)
                context.startActivity(intent);
            }
        }
    }
}
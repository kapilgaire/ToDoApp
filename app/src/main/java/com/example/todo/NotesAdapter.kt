package com.example.todo

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.model.Note
import kotlinx.android.synthetic.main.note_list_row.view.*

class NotesAdapter(private val notesList: List<Note>, val clickListener:(Note)->Unit) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_list_row, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {

        holder.bindItems(notesList[position], clickListener)

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(note: Note, clickListener: (Note) -> Unit) {
            itemView.note.text = note.note
            itemView.dot.text = Html.fromHtml("&#8226;")
            itemView.timestamp.text = note.timeStamp.formatDate()
            itemView.setOnClickListener{clickListener(note)}

        }

    }


}
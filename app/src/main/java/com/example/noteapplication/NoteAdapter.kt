package com.example.noteapplication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(private val noteList: List<Notes>,var mContext: Context) : RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {

       holder.itemView.header.text = noteList[position].noteTitle
        holder.itemView.description.text = noteList[position].noteDetails
        holder.itemView.cardView.setCardBackgroundColor(getRandomColorCode())

          println("id" + holder.itemView.id)

        holder.content.setOnClickListener {
            val intent = Intent(mContext,EditNoteActivity::class.java)

            intent.putExtra("title",noteList[position].noteTitle)
            intent.putExtra("desc", noteList[position].noteDetails)
            intent.putExtra("id",noteList[position].id)

            println("title "+ noteList[position].noteDetails)
            println("desc " + noteList[position].noteTitle)
            println("id " + noteList[position].id)
            mContext.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return noteList.size
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val content = itemView.findViewById(R.id.noteContent) as LinearLayout
        val titleText = itemView.findViewById(R.id.header) as TextView
        val descriptionText = itemView.findViewById<TextView>(R.id.description)

    }

    fun getRandomColorCode(): Int {
        val androidColors: IntArray = mContext.getResources().getIntArray(R.array.androidcolors)
        val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]
        return randomAndroidColor

    }


}
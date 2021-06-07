package com.example.noteapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*

import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var job: Job
    lateinit var text: TextView
    private lateinit var notes: List<Notes>


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()

        notes = ArrayList()
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        text = findViewById(R.id.noteText)


        fab.setOnClickListener { view ->
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
        GlobalScope.launch {
            applicationContext?.let {
                notes = AppDatabase(it).getNoteDao().getAll()
                recyclerView.adapter = NoteAdapter(notes, this@MainActivity)
                (recyclerView.adapter as NoteAdapter).notifyDataSetChanged()
                Log.e("ddd", notes.toString())
            }


            if (notes.isNotEmpty()) {
                text.visibility = View.GONE
                note_img.visibility = View.GONE
            } else {
                text.visibility = View.VISIBLE
                note_img.visibility = View.VISIBLE
            }

        }
    }
}







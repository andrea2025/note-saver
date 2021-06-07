package com.example.noteapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddNoteActivity : AppCompatActivity(), View.OnClickListener,CoroutineScope {
    var note: Notes? = null
    private lateinit var job: Job
   private var mTitle: String? = null
   private var mDescription: String? = null


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        job = Job()

        val save = findViewById(R.id.saveNote) as Button
        save.setOnClickListener(this)
        val img = findViewById<ImageView>(R.id.back)
        img.setOnClickListener(this)

        noteDescription.hint = "Description"

        val bundle = intent.extras

        if (bundle != null) {
            mTitle = bundle.getString("title")
            mDescription = bundle.getString("desc")

        }


        noteTitle.setText(mTitle)
        noteDescription.setText(mDescription)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.saveNote -> {
                val stringTitle = noteTitle.text.toString().trim()
                val stringDesc = noteDescription.text.toString().trim()


                if (stringTitle.isEmpty()) {
                    noteTitle.error = "title required"
                    noteTitle.requestFocus()
                }
                if (stringDesc.isEmpty()) {
                    noteDescription.error = "Description required"
                    noteDescription.requestFocus()
                }


                launch {
                    val mNote = Notes(stringTitle, stringDesc)

                    applicationContext?.let {
                            AppDatabase(it).getNoteDao().addNote(mNote)
                           // println("note created" + AppDatabase(it).getNoteDao().addNote(mNote))
                            Toast.makeText(this@AddNoteActivity, "note Saved", Toast.LENGTH_SHORT)
                                .show()
                    }

                }
                val intent = Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
            }


        }


    }
}

package com.example.noteapplication


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EditNoteActivity: AppCompatActivity(), View.OnClickListener,CoroutineScope {
    var note: Notes? = null
    private lateinit var job: Job
    private var mTitle: String? = null
    private var mDescription: String? = null
    private var idNote: Int? = null
   private var dateText :Int? = null
    lateinit var progressBar: ProgressBar

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        job = Job()

        val save = findViewById(R.id.UpdateNote) as Button
        save.setOnClickListener(this)
        val img = findViewById<ImageView>(R.id.backa)
        img.setOnClickListener(this)

        val delete = findViewById<ImageView>(R.id.delete)
        delete.setOnClickListener(this)
        progressBar = findViewById(R.id.progressBar)



        mTitle = intent.getStringExtra("title")
        mDescription = intent.getStringExtra("desc")
        dateText = intent.getIntExtra("date",0)
        idNote = intent.getIntExtra("id", 0)


        editnoteDescription.hint = "Description"
        editnoteTitle.setText(mTitle)
        editnoteDescription.setText(mDescription)

        println("tt " + mTitle)
        println("dd " + mDescription)
        println("iddd " + idNote)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backa -> {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.UpdateNote -> {
                progressBar.progress
                progressBar.visibility = View.VISIBLE

                val stringTitle = editnoteTitle.text.toString().trim()
                val stringDesc = editnoteDescription.text.toString().trim()


                println("string1" + stringDesc)
                println("string2 " + stringTitle)

                if (stringTitle.isEmpty()) {
                    editnoteTitle.error = "title required"
                    editnoteTitle.requestFocus()
                }
                if (stringDesc.isEmpty()) {
                    editnoteDescription.error = "Description required"
                    editnoteDescription.requestFocus()
                }

                launch {

                    val mNote = Notes(stringTitle, stringDesc)
                    println("note1 " + mNote.id)
                    println("note2" + note?.id)
                    println("note3" + note?.noteTitle)
                    println("note4" + note?.noteDetails)
                    println("note5" + mNote)
                    applicationContext?.let {
                        mNote.id = idNote!!.toInt()
                        AppDatabase(it).getNoteDao().editNote(mNote)

                        Toast.makeText(this@EditNoteActivity, "note Updated", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                val intent = Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
            }


            R.id.delete ->{
                if (idNote != null) {
                    deleteNote()
                } else {
                    Toast.makeText(applicationContext, "cannot delete", Toast.LENGTH_SHORT).show()

                }

            }

        }

    }

    private fun deleteNote() {
        AlertDialog.Builder(this).apply {
            setTitle("Are you sure")
            setMessage("can't undo this operation")
            setPositiveButton("Yes") { DialogInterface, which ->
            launch {
             applicationContext?.let {
                 val bNote = Notes(mTitle!!,mDescription!!,idNote!!.toInt())
                 AppDatabase(it).getNoteDao().deleteNote(bNote)
                 val intent = Intent(applicationContext,MainActivity::class.java)
                 startActivity(intent)
          }
            }
            }
            setNegativeButton("No"){
                DialogInterface, which ->
            }
        }.create().show()

    }
}

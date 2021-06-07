package com.example.noteapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_categories")
data class Notes(
    var noteTitle: String,
    var noteDetails: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0)


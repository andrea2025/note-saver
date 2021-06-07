package com.example.noteapplication

import androidx.room.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM note_categories ORDER BY id DESC")
    suspend fun getAll(): List<Notes>

    @Insert
    suspend fun addNote(noteCategories: Notes)

    @Update
    suspend fun editNote(noteCategories: Notes)

    @Delete
    suspend fun deleteNote(noteCategories: Notes)


}
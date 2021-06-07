package com.example.noteapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Notes::class)], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NotesDao

    companion object {
       @Volatile private var databaseInstance: AppDatabase? = null
        private val LOCk = Any()

        operator fun invoke(context:Context) = databaseInstance ?: synchronized(LOCk){
            databaseInstance ?: buildDatabase(context).also {
                databaseInstance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "npplll").build()
    }
}

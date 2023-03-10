package com.portfolio.noty.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.portfolio.noty.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase(){
    abstract fun notesDao(): NotesDao

    companion object {
        private var instance: NotesDatabase? = null
        fun localDbInstance(context: Context): NotesDatabase? {
            if (instance == null) {
                synchronized(NotesDatabase::class) {
                    instance = buildRoomDb(context)
                }
            }
            return instance
        }

        private fun buildRoomDb(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            NotesDatabase::class.java,
            "notes_db").build()
    }
}
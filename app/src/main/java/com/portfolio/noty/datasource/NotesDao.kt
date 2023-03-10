package com.portfolio.noty.datasource

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.portfolio.noty.model.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    suspend fun getAllNotes(): MutableList<Note>

    @Query("UPDATE notes_table SET note_title = :title, note_body = :body WHERE id = :id")
    suspend fun updateNotes(id: Int?, title: String?, body: String?)
}
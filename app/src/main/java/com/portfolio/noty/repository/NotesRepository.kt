package com.portfolio.noty.repository

import android.app.Application
import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.portfolio.noty.datasource.NotesDao
import com.portfolio.noty.model.Note

class NotesRepository(private val dao: NotesDao) {

    var notesMutableLiveData: MutableLiveData<MutableList<Note>> = MutableLiveData()

    suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    suspend fun updateNote(id: Int?, title: String?, body: String?) {
        dao.updateNotes(id, title, body)
    }

    suspend fun getAllNotes() {
        notesMutableLiveData.postValue(dao.getAllNotes())
    }

}
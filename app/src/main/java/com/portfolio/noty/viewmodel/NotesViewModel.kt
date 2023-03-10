package com.portfolio.noty.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.portfolio.noty.datasource.NotesDao
import com.portfolio.noty.datasource.NotesDatabase
import com.portfolio.noty.model.Note
import com.portfolio.noty.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val notesRepository: NotesRepository
    var notesMutableLiveData: MutableLiveData<MutableList<Note>>

    init {
        val dao = NotesDatabase.localDbInstance(application)?.notesDao() as NotesDao
        notesRepository = NotesRepository(dao)
        notesMutableLiveData = notesRepository.notesMutableLiveData
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { notesRepository.insertNote(note) }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { notesRepository.deleteNote(note) }

    fun updateNote(id: Int?, title: String?, body: String?) = viewModelScope.launch(Dispatchers.IO) { notesRepository.updateNote(id, title, body) }

    fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) { notesRepository.getAllNotes() }
}
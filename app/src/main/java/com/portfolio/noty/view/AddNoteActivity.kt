package com.portfolio.noty.view

import android.app.Activity
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.portfolio.noty.R
import com.portfolio.noty.databinding.ActivityAddNoteBinding
import com.portfolio.noty.model.Note
import com.portfolio.noty.utils.getTodayDate
import com.portfolio.noty.utils.makeToast
import com.portfolio.noty.viewmodel.NotesViewModel

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var notesViewModel: NotesViewModel
    private var note: Note = Note(null, "", "", "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        try {
            note = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("note", Note::class.java) as Note
            } else {
                intent.getSerializableExtra("note") as Note
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        initView(note)
    }

    private fun initView(note: Note) {
        binding.etTitle.setText(note.title)
        binding.etBody.setText(note.body)
        binding.backBtn.setOnClickListener { finish() }
        binding.doneBtn.setOnClickListener { updateNote() }
    }

    private fun updateNote() {
        val title = binding.etTitle.text.toString()
        val body = binding.etBody.text.toString()

        if (!(title.isNullOrEmpty() || body.isNullOrEmpty())) {
            if (note.id == null) {
                val intent = Intent()
                intent.putExtra("note", Note(null, title, body, getTodayDate()))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                notesViewModel.updateNote(note.id, title, body)
            }
        } else {
            makeToast(this, "Empty Note!!!")
        }
    }
}
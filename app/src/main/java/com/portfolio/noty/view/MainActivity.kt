package com.portfolio.noty.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.portfolio.noty.adapter.NoteAdapter
import com.portfolio.noty.databinding.ActivityMainBinding
import com.portfolio.noty.interfaces.NoteHelper
import com.portfolio.noty.model.Note
import com.portfolio.noty.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        notesViewModel.notesMutableLiveData.observe(this) {
            it.let {
                adapter.setData(it)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        notesViewModel.getAllNotes()
    }

    private fun initView() {
        setRecyclerView()
        setFab()
        setSearchView()
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (!text.isNullOrEmpty()) {
                    adapter.filterNotes(text)
                }
                return true
            }
        })
    }

    private fun setFab() {
        val getContentNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note: Note = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getSerializableExtra("note", Note::class.java) as Note
                } else {
                    result.data?.getSerializableExtra("note") as Note
                }
                notesViewModel.insertNote(note)

            }

        }
        binding.addNoteBtn.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            getContentNote.launch(intent)
        }
    }

    private fun setRecyclerView() {
        binding.notesRv.setHasFixedSize(true)
        binding.notesRv.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = NoteAdapter(this, object : NoteHelper {
            override fun deleteNote(note: Note) {
                notesViewModel.deleteNote(note)
            }

        })
        binding.notesRv.adapter = adapter
    }
}
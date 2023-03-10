package com.portfolio.noty.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.noty.R
import com.portfolio.noty.databinding.NoteLayoutBinding
import com.portfolio.noty.interfaces.NoteHelper
import com.portfolio.noty.model.Note
import com.portfolio.noty.view.AddNoteActivity

class NoteAdapter(private val context: Context?, private val helper: NoteHelper) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var noteList: MutableList<Note> = mutableListOf<Note>()
    private var fullList: MutableList<Note> = mutableListOf<Note>()

    inner class NoteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
            val item = itemView
            val binding = NoteLayoutBinding.bind(item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var note = noteList[position]
        holder.binding.apply {
            tvNoteTitle.text = note?.title
            tvNoteTitle.isSelected = true
            tvNoteBody.text = note?.body
            tvNoteDate.text = note?.date
            tvNoteDate.isSelected = true
        }
        holder.item.setBackgroundResource(R.drawable.rounded_gradient_bg)
        holder.item.setOnClickListener { openNote(note) }
        holder.item.setOnLongClickListener {
            popUpDisplay(note, it)
        }
    }

    private fun popUpDisplay(note: Note, it: View): Boolean {
        val popupMenu = PopupMenu(context as Context, it)
        popupMenu.setOnMenuItemClickListener {
            action(it, note)
        }
        popupMenu.inflate(R.menu.pop_up_menu)
        popupMenu.show()
        return true
    }

    private fun action(it: MenuItem?, note: Note): Boolean {
        if (it?.itemId == R.id.delete_note) {
            helper.deleteNote(note)
            return true
        }
        return false
    }

    fun filterNotes(searchKeyWord: String) {
        noteList.clear()
        fullList.forEach { item ->
            if (item?.title?.lowercase()?.contains(searchKeyWord.lowercase()) == true ||
                item?.body?.lowercase()?.contains(searchKeyWord.lowercase()) == true) {

                noteList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    private fun openNote(note: Note) {
        val intent = Intent(context, AddNoteActivity::class.java)
        intent.putExtra("note", note)
        context?.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(NoteList: MutableList<Note>) {
        fullList.clear()
        fullList.addAll(NoteList)

        noteList.clear()
        noteList.addAll(NoteList)
        notifyDataSetChanged() //to notify adapter that new data change has been happened to adapt it
    }
}
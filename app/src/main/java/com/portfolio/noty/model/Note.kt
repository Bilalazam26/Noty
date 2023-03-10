package com.portfolio.noty.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @ColumnInfo(name = "note_title")
    var title: String?,
    @ColumnInfo(name = "note_body")
    var body: String?,
    @ColumnInfo(name = "note_date")
    val date: String?
) : Serializable

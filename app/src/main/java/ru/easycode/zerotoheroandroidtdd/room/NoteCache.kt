package ru.easycode.zerotoheroandroidtdd.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteCache(
    @PrimaryKey @ColumnInfo("id") val id: Long,
    @ColumnInfo("text") val text: String,
    @ColumnInfo("folderId") val folderId: Long,
)

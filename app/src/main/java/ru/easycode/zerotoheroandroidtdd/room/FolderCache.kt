package ru.easycode.zerotoheroandroidtdd.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_table")
data class FolderCache(
    @PrimaryKey @ColumnInfo("id") val id: Long,
    @ColumnInfo("text") val text: String,
)
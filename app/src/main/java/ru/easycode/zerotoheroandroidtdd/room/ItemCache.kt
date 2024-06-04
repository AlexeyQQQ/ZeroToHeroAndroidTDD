package ru.easycode.zerotoheroandroidtdd.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class ItemCache(
    @PrimaryKey @ColumnInfo("id") val id: Long,
    @ColumnInfo("text") val text: String,
)
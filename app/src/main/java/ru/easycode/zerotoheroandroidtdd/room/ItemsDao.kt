package ru.easycode.zerotoheroandroidtdd.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsDao {

    @Query("SELECT * FROM item_table")
    fun list(): List<ItemCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: ItemCache)

    @Query("SELECT * FROM item_table WHERE id = :id")
    fun item(id: Long): ItemCache

    @Query("DELETE FROM item_table WHERE id = :id")
    fun delete(id: Long)
}
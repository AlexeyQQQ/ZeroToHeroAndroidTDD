package ru.easycode.zerotoheroandroidtdd.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemCache::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun dao(): ItemsDao
}
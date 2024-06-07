package ru.easycode.zerotoheroandroidtdd.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FolderCache::class,
        NoteCache::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    abstract fun foldersDao(): FoldersDao
}
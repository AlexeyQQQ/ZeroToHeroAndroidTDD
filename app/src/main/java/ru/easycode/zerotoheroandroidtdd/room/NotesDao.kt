package ru.easycode.zerotoheroandroidtdd.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteCache)

    @Query("SELECT * FROM note_table WHERE id = :noteId")
    suspend fun note(noteId: Long): NoteCache

    @Query("SELECT * FROM note_table WHERE folderId = :folderId")
    suspend fun notes(folderId: Long): List<NoteCache>

    @Query("DELETE FROM note_table WHERE id = :noteId")
    suspend fun delete(noteId: Long)

    @Query("DELETE FROM note_table WHERE folderId = :folderId")
    suspend fun deleteByFolderId(folderId: Long)
}

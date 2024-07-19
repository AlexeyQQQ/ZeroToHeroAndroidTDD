package ru.easycode.zerotoheroandroidtdd.core.data

import ru.easycode.zerotoheroandroidtdd.core.data.room.NoteCache
import ru.easycode.zerotoheroandroidtdd.core.data.room.NotesDao

interface NotesRepository {

    interface ReadList {
        suspend fun noteList(folderId: Long): List<MyNote>
    }

    interface Create {
        suspend fun createNote(folderId: Long, text: String): Long
    }

    interface Edit {
        suspend fun deleteNote(noteId: Long)
        suspend fun renameNote(noteId: Long, newName: String)
        suspend fun note(noteId: Long): MyNote
    }

    class Base(
        private val now: Now,
        private val notesDao: NotesDao,
    ) : ReadList, Create, Edit {

        override suspend fun noteList(folderId: Long): List<MyNote> {
            return notesDao.notes(folderId).map {
                MyNote(it.id, it.text, it.folderId)
            }
        }

        override suspend fun createNote(folderId: Long, text: String): Long {
            val nowTime = now.timeInMillis()
            notesDao.insert(NoteCache(nowTime, text, folderId))
            return nowTime
        }

        override suspend fun deleteNote(noteId: Long) {
            notesDao.delete(noteId)
        }

        override suspend fun renameNote(noteId: Long, newName: String) {
            val oldNote = notesDao.note(noteId)
            notesDao.insert(oldNote.copy(text = newName))
        }

        override suspend fun note(noteId: Long): MyNote {
            val noteCache = notesDao.note(noteId)
            return MyNote(noteCache.id, noteCache.text, noteCache.folderId)
        }
    }
}

package ru.easycode.zerotoheroandroidtdd

import ru.easycode.zerotoheroandroidtdd.room.FolderCache
import ru.easycode.zerotoheroandroidtdd.room.FoldersDao
import ru.easycode.zerotoheroandroidtdd.room.NotesDao

interface FoldersRepository {

    interface ReadList {
        suspend fun folders(): List<Folder>
    }

    interface Edit {
        suspend fun delete(folderId: Long)
        suspend fun rename(folderId: Long, newName: String)
    }

    interface Create {
        suspend fun createFolder(name: String): Long
    }

    class Base(
        private val now: Now,
        private val foldersDao: FoldersDao,
        private val notesDao: NotesDao,
    ) : ReadList, Edit, Create {

        override suspend fun folders(): List<Folder> {
            return foldersDao.folders().map {
                Folder(it.id, it.text, notesDao.notes(it.id).size)
            }
        }

        override suspend fun delete(folderId: Long) {
            foldersDao.delete(folderId)
            notesDao.deleteByFolderId(folderId)
        }

        override suspend fun rename(folderId: Long, newName: String) {
            foldersDao.insert(FolderCache(folderId, newName))
        }

        override suspend fun createFolder(name: String): Long {
            val nowTime = now.timeInMillis()
            foldersDao.insert(FolderCache(nowTime, name))
            return nowTime
        }
    }
}
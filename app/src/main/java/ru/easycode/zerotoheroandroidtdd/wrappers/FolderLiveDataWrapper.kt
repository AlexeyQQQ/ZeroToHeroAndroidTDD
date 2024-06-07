package ru.easycode.zerotoheroandroidtdd.wrappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.FolderUi

interface FolderLiveDataWrapper {

    interface Update {
        fun update(value: FolderUi)
    }

    interface FolderId {
        fun folderId(): Long
    }

    interface Read {
        fun liveData(): LiveData<FolderUi> = throw Exception()
    }

    interface Mutable : Update, FolderId, Read

    interface Rename : Read {
        fun rename(newName: String)
    }

    interface Increment {
        fun increment()
    }

    interface Decrement {
        fun decrement()
    }

    class Base(
        private val liveData: MutableLiveData<FolderUi> = MutableLiveData(),
    ) : Mutable, Rename, Increment, Decrement {

        override fun liveData(): LiveData<FolderUi> {
            return liveData
        }

        override fun update(value: FolderUi) {
            liveData.value = value
        }

        override fun folderId(): Long {
            return liveData.value?.id ?: throw RuntimeException("FolderUi == null")
        }

        override fun rename(newName: String) {
            val oldFolder = liveData.value
                ?: throw RuntimeException("FolderUi == null")
            val newFolder = oldFolder.copy(title = newName)
            liveData.value = newFolder
        }

        override fun increment() {
            val oldFolder = liveData.value
                ?: throw RuntimeException("FolderUi == null")
            val newFolder = oldFolder.copy(notesCount = oldFolder.notesCount + 1)
            liveData.value = newFolder
        }

        override fun decrement() {
            val oldFolder = liveData.value
                ?: throw RuntimeException("FolderUi == null")
            val newFolder = oldFolder.copy(notesCount = oldFolder.notesCount - 1)
            liveData.value = newFolder
        }
    }
}
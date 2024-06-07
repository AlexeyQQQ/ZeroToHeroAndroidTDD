package ru.easycode.zerotoheroandroidtdd.wrappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.NoteUi

class NoteListLiveDataWrapper {

    interface UpdateListAndRead {
        fun update(value: List<NoteUi>)
        fun liveData(): LiveData<List<NoteUi>> = throw Exception()
    }

    interface Create {
        fun create(noteUi: NoteUi)
    }

    interface Update {
        fun update(noteId: Long, newText: String)
    }

    class Base(
        private val liveData: MutableLiveData<List<NoteUi>> = MutableLiveData()
    ) : UpdateListAndRead, Create, Update {

        override fun update(value: List<NoteUi>) {
            liveData.value = value
        }

        override fun liveData(): LiveData<List<NoteUi>> {
            return liveData
        }

        override fun create(noteUi: NoteUi) {
            val mutableList = liveData.value?.toMutableList() ?: mutableListOf()
            mutableList.add(noteUi)
            liveData.value = mutableList
        }

        override fun update(noteId: Long, newText: String) {
            val mutableList = liveData.value?.toMutableList() ?: mutableListOf()
            mutableList.find { it.id == noteId }?.let {
                mutableList[mutableList.indexOf(it)] = it.copy(title = newText)
            }
            liveData.value = mutableList
        }
    }
}

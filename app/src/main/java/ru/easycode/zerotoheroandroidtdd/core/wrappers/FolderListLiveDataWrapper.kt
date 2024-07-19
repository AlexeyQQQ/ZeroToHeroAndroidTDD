package ru.easycode.zerotoheroandroidtdd.core.wrappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.data.FolderUi

class FolderListLiveDataWrapper {

    interface UpdateListAndRead {
        fun update(list: List<FolderUi>)
        fun liveData(): LiveData<List<FolderUi>> = throw Exception()
    }

    interface Create {
        fun create(folderUi: FolderUi)
    }

    class Base(
        private val liveData: MutableLiveData<List<FolderUi>> = MutableLiveData(),
    ) : UpdateListAndRead, Create {

        override fun create(folderUi: FolderUi) {
            val mutableList = liveData.value?.toMutableList() ?: mutableListOf()
            mutableList.add(folderUi)
            liveData.value = mutableList
        }

        override fun liveData(): LiveData<List<FolderUi>> {
            return liveData
        }

        override fun update(list: List<FolderUi>) {
            liveData.value = list
        }
    }
}
package ru.easycode.zerotoheroandroidtdd.wrappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.repository.ItemUi

interface ListLiveDataWrapper {

    interface Read {
        fun liveData(): LiveData<List<ItemUi>>
    }

    interface UpdateList {
        fun update(list: List<ItemUi>)
    }

    interface UpdateItem {
        fun update(item: ItemUi)
    }

    interface Add {
        fun add(value: ItemUi)
    }

    interface Delete {
        fun delete(item: ItemUi)
    }

    interface All : Read, UpdateList, UpdateItem, Add, Delete

    class Base(
        private val liveData: MutableLiveData<List<ItemUi>> = MutableLiveData(),
    ) : All {

        override fun liveData(): LiveData<List<ItemUi>> {
            return liveData
        }

        override fun update(list: List<ItemUi>) {
            liveData.value = list
        }

        override fun update(item: ItemUi) {
            val mutableList = liveData.value?.toMutableList() ?: mutableListOf()
            mutableList.find { it.areItemsSame(item) }?.let {
                mutableList[mutableList.indexOf(it)] = item
            }
            liveData.value = mutableList
        }

        override fun add(value: ItemUi) {
            val mutableList = liveData.value?.toMutableList() ?: mutableListOf()
            mutableList.add(value)
            liveData.value = mutableList
        }

        override fun delete(item: ItemUi) {
            val mutableList = liveData.value?.toMutableList() ?: mutableListOf()
            mutableList.remove(item)
            liveData.value = mutableList
        }
    }
}
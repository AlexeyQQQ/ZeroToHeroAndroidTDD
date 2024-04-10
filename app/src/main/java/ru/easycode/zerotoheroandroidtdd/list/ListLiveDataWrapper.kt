package ru.easycode.zerotoheroandroidtdd.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.BundleWrapper
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.core.SingleLiveEvent

interface ListLiveDataWrapper {

    interface Read : LiveDataWrapper.Read<List<CharSequence>>

    interface Update : LiveDataWrapper.Update<List<CharSequence>>

    interface Save {
        fun save(bundleWrapper: BundleWrapper.Save)
    }

    interface Mutable : Read, Update, Save

    interface Add {
        fun add(source: CharSequence)
    }

    interface All : Mutable, Add


    class Base : LiveDataWrapper.Abstract<List<CharSequence>>(), All {

        override fun add(source: CharSequence) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            list.add(source)
            update(list)
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            val list = liveData.value as ArrayList
            bundleWrapper.save(list)
        }
    }
}
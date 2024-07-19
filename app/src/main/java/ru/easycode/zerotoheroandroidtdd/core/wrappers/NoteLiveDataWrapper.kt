package ru.easycode.zerotoheroandroidtdd.core.wrappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface NoteLiveDataWrapper {

    fun update(noteText: String)

    fun liveData(): LiveData<String> = throw Exception()

    class Base(
        private val liveData: MutableLiveData<String> = MutableLiveData(),
    ) : NoteLiveDataWrapper {

        override fun liveData(): LiveData<String> {
            return liveData
        }

        override fun update(noteText: String) {
            liveData.value = noteText
        }
    }
}


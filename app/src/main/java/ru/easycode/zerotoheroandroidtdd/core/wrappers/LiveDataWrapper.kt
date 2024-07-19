package ru.easycode.zerotoheroandroidtdd.core.wrappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    interface Read<T : Any> {
        fun liveData(): LiveData<T>
    }

    interface Update<T : Any> {
        fun update(value: T)
    }

    interface Mutable<T : Any> : Read<T>, Update<T>


    abstract class Abstract<T : Any>(
        protected val liveData: MutableLiveData<T> = MutableLiveData<T>(),
    ) : Mutable<T> {

        override fun liveData(): LiveData<T> {
            return liveData
        }

        override fun update(value: T) {
            liveData.value = value
        }
    }
}
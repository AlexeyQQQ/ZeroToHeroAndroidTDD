package ru.easycode.zerotoheroandroidtdd.wrappers

import androidx.lifecycle.LiveData
import ru.easycode.zerotoheroandroidtdd.UiState

interface LiveDataWrapper {

    interface Save : LiveDataWrapper {
        fun save(bundleWrapper: BundleWrapper.Save)
    }

    interface Update : LiveDataWrapper {
        fun update(value: UiState)
    }

    interface Observe : LiveDataWrapper {
        fun liveData(): LiveData<UiState>
    }

    interface Mutable : Save, Update, Observe


    class Base(
        private val liveData: SingleLiveEvent<UiState> = SingleLiveEvent<UiState>(),
    ) : Mutable {

        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveData.value?.let { bundleWrapper.save(it) }
        }

        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun liveData(): LiveData<UiState> {
            return liveData
        }
    }
}
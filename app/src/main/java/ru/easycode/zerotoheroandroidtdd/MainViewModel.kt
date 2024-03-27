package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val listLiveDataWrapper: ListLiveDataWrapper,
) : ViewModel() {

    val liveData = listLiveDataWrapper.liveData()

    fun add(text: CharSequence) {
        listLiveDataWrapper.add(text)
    }

    fun save(bundle: BundleWrapper.Save) {
        listLiveDataWrapper.save(bundle)
    }

    fun restore(bundle: BundleWrapper.Restore) {
        val restoreList = bundle.restore()
        listLiveDataWrapper.update(restoreList)
    }
}
package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val listLiveDataWrapper: ListLiveDataWrapper,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(listLiveDataWrapper) as T
        } else {
            throw RuntimeException("Unknown ViewModel class $modelClass")
        }
    }
}
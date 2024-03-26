package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.easycode.zerotoheroandroidtdd.repository.Repository
import ru.easycode.zerotoheroandroidtdd.wrappers.LiveDataWrapper

class ViewModelFactory(
    private val repository: Repository,
    private val liveDataWrapper: LiveDataWrapper.Mutable,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository, liveDataWrapper) as T
        } else {
            throw RuntimeException("Unknown ViewModel class $modelClass")
        }
    }
}
package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.easycode.zerotoheroandroidtdd.repository.Repository
import ru.easycode.zerotoheroandroidtdd.wrappers.BundleWrapper
import ru.easycode.zerotoheroandroidtdd.wrappers.LiveDataWrapper

class MainViewModel(
    private val repository: Repository,
    private val liveDataWrapper: LiveDataWrapper,
) : ViewModel() {

    fun liveData(): LiveData<UiState> {
        return liveDataWrapper.liveData()
    }

    fun load() {
        liveDataWrapper.update(UiState.ShowProgress)
        viewModelScope.launch {
            val response = repository.load()
            liveDataWrapper.update(UiState.ShowData(response.text))
        }
    }

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
        liveDataWrapper.update(bundleWrapper.restore())
    }
}
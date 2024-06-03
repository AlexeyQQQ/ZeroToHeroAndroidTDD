package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.wrappers.ListLiveDataWrapper

class ListViewModel(
    private val repository: Repository.Read,
    private val liveDataWrapper: ListLiveDataWrapper.Mutable,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(dispatcherMain)

    fun init() {
        coroutineScope.launch(dispatcher) {
            val list = repository.list()
            withContext(dispatcherMain) {
                liveDataWrapper.update(list)
            }
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
    }

    fun liveData() = liveDataWrapper.liveData()
}

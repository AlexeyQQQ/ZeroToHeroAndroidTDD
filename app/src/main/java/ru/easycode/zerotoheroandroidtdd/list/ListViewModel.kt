package ru.easycode.zerotoheroandroidtdd.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.repository.Repository
import ru.easycode.zerotoheroandroidtdd.wrappers.ListLiveDataWrapper

class ListViewModel(
    private val repository: Repository.Read,
    private val liveDataWrapper: ListLiveDataWrapper.All,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate,
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
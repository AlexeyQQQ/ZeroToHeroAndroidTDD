package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.di.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.wrappers.ListLiveDataWrapper

class AddViewModel(
    private val repository: Repository.Add,
    private val liveDataWrapper: ListLiveDataWrapper.Add,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(dispatcherMain)

    fun add(value: String) {
        coroutineScope.launch(dispatcher) {
            repository.add(value)
            withContext(dispatcherMain) {
                liveDataWrapper.add(value)
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(AddViewModel::class.java)
    }
}
package ru.easycode.zerotoheroandroidtdd.add

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.repository.ItemUi
import ru.easycode.zerotoheroandroidtdd.repository.Repository
import ru.easycode.zerotoheroandroidtdd.di.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.wrappers.ListLiveDataWrapper

class AddViewModel(
    private val repository: Repository.Add,
    private val liveDataWrapper: ListLiveDataWrapper.Add,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(dispatcherMain)

    fun add(value: String) {
        coroutineScope.launch(dispatcher) {
            val nowTime = repository.add(value)
            withContext(dispatcherMain) {
                liveDataWrapper.add(ItemUi(nowTime, value))
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(AddViewModel::class.java)
    }

    override fun onCleared() {
        coroutineScope.cancel()
    }
}
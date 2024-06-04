package ru.easycode.zerotoheroandroidtdd.details

import androidx.lifecycle.MutableLiveData
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

class DetailsViewModel(
    private val changeLiveDataWrapper: ListLiveDataWrapper.All,
    private val repository: Repository.Change,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(dispatcherMain)

    val liveData = MutableLiveData<String>()

    fun init(itemId: Long) {
        coroutineScope.launch(dispatcher) {
            val value = repository.item(itemId).text
            withContext(dispatcherMain) {
                liveData.value = value
            }
        }
    }

    fun delete(itemId: Long) {
        coroutineScope.launch(dispatcher) {
            val item = repository.item(itemId)
            repository.delete(itemId)
            withContext(dispatcherMain) {
                changeLiveDataWrapper.delete(item)
                comeback()
            }
        }
    }

    fun update(itemId: Long, newText: String) {
        coroutineScope.launch(dispatcher) {
            repository.update(itemId, newText)
            withContext(dispatcherMain) {
                changeLiveDataWrapper.update(ItemUi(itemId, newText))
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(DetailsViewModel::class.java)
    }

    override fun onCleared() {
        coroutineScope.cancel()
    }
}
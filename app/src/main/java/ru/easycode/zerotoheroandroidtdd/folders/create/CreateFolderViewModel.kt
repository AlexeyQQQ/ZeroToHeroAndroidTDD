package ru.easycode.zerotoheroandroidtdd.folders.create

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.data.FolderUi
import ru.easycode.zerotoheroandroidtdd.core.di.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.data.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderListLiveDataWrapper

class CreateFolderViewModel(
    private val repository: FoldersRepository.Create,
    private val folderListLiveDataWrapper: FolderListLiveDataWrapper.Create,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    private val scope = CoroutineScope(dispatcherMain)

    fun createFolder(name: String) {
        scope.launch(dispatcher) {
            val id = repository.createFolder(name)
            withContext(dispatcherMain) {
                folderListLiveDataWrapper.create(FolderUi(id, name, 0))
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clear(CreateFolderViewModel::class.java)
        navigation.update(Screen.PopBackStack)
//        navigation.update(FoldersListScreen)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
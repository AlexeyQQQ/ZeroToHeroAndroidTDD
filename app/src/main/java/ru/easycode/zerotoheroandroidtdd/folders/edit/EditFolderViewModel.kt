package ru.easycode.zerotoheroandroidtdd.folders.edit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.di.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.data.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folders.details.FolderDetailsViewModel
import ru.easycode.zerotoheroandroidtdd.core.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderLiveDataWrapper

class EditFolderViewModel(
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Rename,
    private val repository: FoldersRepository.Edit,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    private val scope = CoroutineScope(dispatcherMain)

    fun liveData() = folderLiveDataWrapper.liveData()

    fun renameFolder(folderId: Long, newName: String) {
        scope.launch(dispatcher) {
            repository.rename(folderId, newName)
            withContext(dispatcherMain) {
                folderLiveDataWrapper.rename(newName)
                comeback()
            }
        }
    }

    fun deleteFolder(folderId: Long) {
        scope.launch(dispatcher) {
            repository.delete(folderId)
            withContext(dispatcherMain) {
                clear.clear(EditFolderViewModel::class.java, FolderDetailsViewModel::class.java)
                navigation.update(FoldersListScreen)
            }
        }
    }

    fun comeback() {
        clear.clear(EditFolderViewModel::class.java)
        navigation.update(Screen.PopBackStack)
//        navigation.update(FolderDetailsScreen)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
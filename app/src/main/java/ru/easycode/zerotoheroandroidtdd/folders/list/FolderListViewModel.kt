package ru.easycode.zerotoheroandroidtdd.folders.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.data.FolderUi
import ru.easycode.zerotoheroandroidtdd.core.data.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.core.CreateFolderScreen
import ru.easycode.zerotoheroandroidtdd.core.FolderDetailsScreen
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderLiveDataWrapper

class FolderListViewModel(
    private val repository: FoldersRepository.ReadList,
    private val listLiveDataWrapper: FolderListLiveDataWrapper.UpdateListAndRead,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Update,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    private val scope = CoroutineScope(dispatcherMain)

    fun liveData() = listLiveDataWrapper.liveData()

    fun init() {
        scope.launch(dispatcher) {
            val listFolders = repository.folders().map {
                FolderUi(it.id, it.title, it.notesCount)
            }
            withContext(dispatcherMain) {
                listLiveDataWrapper.update(listFolders)
            }
        }
    }

    fun addFolder() {
        navigation.update(CreateFolderScreen)
    }

    fun folderDetails(folderUi: FolderUi) {
        folderLiveDataWrapper.update(folderUi)
        navigation.update(FolderDetailsScreen)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
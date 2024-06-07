package ru.easycode.zerotoheroandroidtdd.folders.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.NoteUi
import ru.easycode.zerotoheroandroidtdd.NotesRepository
import ru.easycode.zerotoheroandroidtdd.di.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.main.CreateNoteScreen
import ru.easycode.zerotoheroandroidtdd.main.EditFolderScreen
import ru.easycode.zerotoheroandroidtdd.main.EditNoteScreen
import ru.easycode.zerotoheroandroidtdd.main.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.main.Screen
import ru.easycode.zerotoheroandroidtdd.wrappers.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.wrappers.NoteListLiveDataWrapper

class FolderDetailsViewModel(
    private val noteListRepository: NotesRepository.ReadList,
    private val noteListLiveDataWrapper: NoteListLiveDataWrapper.UpdateListAndRead,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Mutable,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    private val scope = CoroutineScope(dispatcherMain)

    fun liveDataListNote() = noteListLiveDataWrapper.liveData()

    fun liveDataFolderInfo() = folderLiveDataWrapper.liveData()

    fun init() {
        scope.launch(dispatcher) {
            val folderId = folderLiveDataWrapper.folderId()
            val listNotes = noteListRepository.noteList(folderId).map {
                NoteUi(it.id, it.title, it.folderId)
            }
            withContext(dispatcherMain) {
                noteListLiveDataWrapper.update(listNotes)
            }
        }
    }

    fun createNote() {
        val folderId = folderLiveDataWrapper.folderId()
        navigation.update(CreateNoteScreen(folderId))
    }

    fun editNote(noteUi: NoteUi) {
        navigation.update(EditNoteScreen(noteUi.id))
    }

    fun editFolder() {
        val folderId = folderLiveDataWrapper.folderId()
        navigation.update(EditFolderScreen(folderId))
    }

    fun comeback() {
        clear.clear(FolderDetailsViewModel::class.java)
        navigation.update(Screen.PopBackStack)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
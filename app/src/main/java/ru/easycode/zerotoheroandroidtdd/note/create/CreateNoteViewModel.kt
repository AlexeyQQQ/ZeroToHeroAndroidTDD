package ru.easycode.zerotoheroandroidtdd.note.create

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.NoteUi
import ru.easycode.zerotoheroandroidtdd.di.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.NotesRepository
import ru.easycode.zerotoheroandroidtdd.main.FolderDetailsScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.main.Screen
import ru.easycode.zerotoheroandroidtdd.wrappers.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.wrappers.NoteListLiveDataWrapper

class CreateNoteViewModel(
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Increment,
    private val noteListLiveDataWrapper: NoteListLiveDataWrapper.Create,
    private val repository: NotesRepository.Create,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    private val scope = CoroutineScope(dispatcherMain)

    fun createNote(folderId: Long, text: String) {
        scope.launch(dispatcher) {
            val id = repository.createNote(folderId, text)
            withContext(dispatcherMain) {
                folderLiveDataWrapper.increment()
                noteListLiveDataWrapper.create(NoteUi(id, text, folderId))
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clear(CreateNoteViewModel::class.java)
        navigation.update(Screen.PopBackStack)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
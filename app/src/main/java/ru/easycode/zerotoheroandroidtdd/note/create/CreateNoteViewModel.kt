package ru.easycode.zerotoheroandroidtdd.note.create

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.data.NoteUi
import ru.easycode.zerotoheroandroidtdd.core.di.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.data.NotesRepository
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.core.wrappers.NoteListLiveDataWrapper

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
//        navigation.update(FolderDetailsScreen)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
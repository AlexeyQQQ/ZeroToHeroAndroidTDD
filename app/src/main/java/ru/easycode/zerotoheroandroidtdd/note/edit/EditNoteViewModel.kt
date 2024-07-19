package ru.easycode.zerotoheroandroidtdd.note.edit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.data.NotesRepository
import ru.easycode.zerotoheroandroidtdd.core.di.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.core.wrappers.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.core.wrappers.NoteLiveDataWrapper

class EditNoteViewModel(
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Decrement,
    private val noteLiveDataWrapper: NoteLiveDataWrapper,
    private val noteListLiveDataWrapper: NoteListLiveDataWrapper.Update,
    private val repository: NotesRepository.Edit,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    private val scope = CoroutineScope(dispatcherMain)

    fun liveData() = noteLiveDataWrapper.liveData()

    fun init(noteId: Long) {
        scope.launch(dispatcher) {
            val note = repository.note(noteId)
            withContext(dispatcherMain) {
                noteLiveDataWrapper.update(note.title)
            }
        }
    }

    fun deleteNote(noteId: Long) {
        scope.launch(dispatcher) {
            repository.deleteNote(noteId)
            withContext(dispatcherMain) {
                folderLiveDataWrapper.decrement()
                comeback()
            }
        }
    }

    fun renameNote(noteId: Long, newText: String) {
        scope.launch(dispatcher) {
            repository.renameNote(noteId, newText)
            withContext(dispatcherMain) {
                noteListLiveDataWrapper.update(noteId, newText)
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clear(EditNoteViewModel::class.java)
        navigation.update(Screen.PopBackStack)
//        navigation.update(FolderDetailsScreen)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
package ru.easycode.zerotoheroandroidtdd.core.di

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.folders.create.CreateFolderViewModel
import ru.easycode.zerotoheroandroidtdd.folders.details.FolderDetailsViewModel
import ru.easycode.zerotoheroandroidtdd.folders.edit.EditFolderViewModel
import ru.easycode.zerotoheroandroidtdd.folders.list.FolderListViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteViewModel
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteViewModel
import ru.easycode.zerotoheroandroidtdd.core.wrappers.NoteLiveDataWrapper

interface ViewModelFactory : ProvideViewModel, ClearViewModels

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(clasz: Class<T>): T

    class Factory(
        private val provideViewModel: ProvideViewModel,
    ) : ViewModelFactory {

        private val map = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return if (map.contains(clasz)) {
                map[clasz] as T
            } else {
                val viewModel = provideViewModel.viewModel(clasz)
                map[clasz] = viewModel
                viewModel
            }
        }

        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            viewModelClasses.forEach { map.remove(it) }
        }
    }

    class Base(
        private val clearViewModel: ClearViewModels,
        private val core: Core,
    ) : ProvideViewModel {

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return when (clasz) {

                MainViewModel::class.java -> MainViewModel(
                    navigation = core.navigation,
                )

                FolderListViewModel::class.java -> FolderListViewModel(
                    repository = core.foldersRepository,
                    listLiveDataWrapper = core.folderListLiveDataWrapper,
                    folderLiveDataWrapper = core.folderLiveDataWrapper,
                    navigation = core.navigation,
                )

                FolderDetailsViewModel::class.java -> FolderDetailsViewModel(
                    noteListRepository = core.notesRepository,
                    noteListLiveDataWrapper = core.noteListLiveDataWrapper,
                    folderLiveDataWrapper = core.folderLiveDataWrapper,
                    navigation = core.navigation,
                    clear = clearViewModel,
                )

                CreateFolderViewModel::class.java -> CreateFolderViewModel(
                    repository = core.foldersRepository,
                    folderListLiveDataWrapper = core.folderListLiveDataWrapper,
                    navigation = core.navigation,
                    clear = clearViewModel,
                )

                EditFolderViewModel::class.java -> EditFolderViewModel(
                    folderLiveDataWrapper = core.folderLiveDataWrapper,
                    repository = core.foldersRepository,
                    navigation = core.navigation,
                    clear = clearViewModel,
                )

                CreateNoteViewModel::class.java -> CreateNoteViewModel(
                    folderLiveDataWrapper = core.folderLiveDataWrapper,
                    noteListLiveDataWrapper = core.noteListLiveDataWrapper,
                    repository = core.notesRepository,
                    navigation = core.navigation,
                    clear = clearViewModel,
                )

                EditNoteViewModel::class.java -> EditNoteViewModel(
                    folderLiveDataWrapper = core.folderLiveDataWrapper,
                    noteLiveDataWrapper = NoteLiveDataWrapper.Base(),
                    noteListLiveDataWrapper = core.noteListLiveDataWrapper,
                    repository = core.notesRepository,
                    navigation = core.navigation,
                    clear = clearViewModel,
                )

                else -> throw IllegalStateException("Unknown viewModelClass $clasz")
            } as T
        }
    }
}
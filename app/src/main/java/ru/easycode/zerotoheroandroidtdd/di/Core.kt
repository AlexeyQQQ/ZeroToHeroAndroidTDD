package ru.easycode.zerotoheroandroidtdd.di

import android.content.Context
import androidx.room.Room
import ru.easycode.zerotoheroandroidtdd.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.NotesRepository
import ru.easycode.zerotoheroandroidtdd.Now
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.room.AppDataBase
import ru.easycode.zerotoheroandroidtdd.wrappers.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.wrappers.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.wrappers.NoteListLiveDataWrapper

class Core(
    context: Context,
    val navigation: Navigation.All,
) {

    private val database by lazy {
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            AppDataBase::class.java.simpleName,
        ).build()
    }

    val folderLiveDataWrapper = FolderLiveDataWrapper.Base()

    val folderListLiveDataWrapper = FolderListLiveDataWrapper.Base()

    val noteListLiveDataWrapper = NoteListLiveDataWrapper.Base()

    val foldersRepository: FoldersRepository.Base =
        FoldersRepository.Base(Now.Base(), database.foldersDao(), database.notesDao())

    val notesRepository: NotesRepository.Base = NotesRepository.Base(Now.Base(), database.notesDao())

}
package ru.easycode.zerotoheroandroidtdd.core.di

import android.content.Context
import androidx.room.Room
import ru.easycode.zerotoheroandroidtdd.core.data.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.core.data.NotesRepository
import ru.easycode.zerotoheroandroidtdd.core.data.Now
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.data.room.AppDataBase
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.core.wrappers.NoteListLiveDataWrapper

class Core(
    context: Context,
) {

    private val database by lazy {
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            AppDataBase::class.java.simpleName,
        ).build()
    }

    val navigation = Navigation.Base()

    val folderLiveDataWrapper = FolderLiveDataWrapper.Base()

    val folderListLiveDataWrapper = FolderListLiveDataWrapper.Base()

    val noteListLiveDataWrapper = NoteListLiveDataWrapper.Base()

    val foldersRepository: FoldersRepository.Base =
        FoldersRepository.Base(Now.Base(), database.foldersDao(), database.notesDao())

    val notesRepository: NotesRepository.Base =
        NotesRepository.Base(Now.Base(), database.notesDao())

}
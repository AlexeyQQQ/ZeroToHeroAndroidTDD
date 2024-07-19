package ru.easycode.zerotoheroandroidtdd.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.folders.create.CreateFolderFragment
import ru.easycode.zerotoheroandroidtdd.folders.details.FolderDetailsFragment
import ru.easycode.zerotoheroandroidtdd.folders.edit.EditFolderFragment
import ru.easycode.zerotoheroandroidtdd.folders.list.FolderListFragment
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteFragment
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteFragment
import ru.easycode.zerotoheroandroidtdd.core.wrappers.SingleLiveEvent

interface Navigation {

    interface Read {
        fun liveData(): LiveData<Screen> = throw Exception()
    }

    interface Update {
        fun update(screen: Screen)
    }

    interface Mutable : Read, Update

    class Base(
        private val liveData: MutableLiveData<Screen> = SingleLiveEvent<Screen>(),
    ) : Mutable {

        override fun liveData(): LiveData<Screen> {
            return liveData
        }

        override fun update(screen: Screen) {
            liveData.value = screen
        }
    }
}

interface Screen {

    fun show(containerId: Int, fragmentManager: FragmentManager)

    abstract class Replace : Screen {

        abstract fun fragment(): Fragment

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(R.id.container, fragment())
                .commit()
        }
    }

    abstract class ReplaceAndAddToBackstack : Screen {

        abstract fun fragment(): Fragment

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(R.id.container, fragment())
                .addToBackStack(fragment().javaClass.simpleName)
                .commit()
        }
    }

    object PopBackStack : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.popBackStack()
        }
    }
}


object FoldersListScreen : Screen.Replace() {
    override fun fragment(): Fragment = FolderListFragment()
}

object FolderDetailsScreen : Screen.ReplaceAndAddToBackstack() {
    override fun fragment(): Fragment = FolderDetailsFragment()
}

object CreateFolderScreen : Screen.ReplaceAndAddToBackstack() {
    override fun fragment(): Fragment = CreateFolderFragment()
}

data class EditFolderScreen(
    private val folderId: Long,
) : Screen.ReplaceAndAddToBackstack() {
    override fun fragment(): Fragment = EditFolderFragment.newInstance(folderId)
}

data class CreateNoteScreen(
    private val folderId: Long,
) : Screen.ReplaceAndAddToBackstack() {
    override fun fragment(): Fragment = CreateNoteFragment.newInstance(folderId)
}

data class EditNoteScreen(
    private val noteId: Long,
) : Screen.ReplaceAndAddToBackstack() {
    override fun fragment(): Fragment = EditNoteFragment.newInstance(noteId)
}
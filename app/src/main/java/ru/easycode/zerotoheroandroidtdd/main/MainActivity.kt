package ru.easycode.zerotoheroandroidtdd.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.App
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.folders.create.CreateFolderFragment
import ru.easycode.zerotoheroandroidtdd.folders.details.FolderDetailsFragment
import ru.easycode.zerotoheroandroidtdd.folders.edit.EditFolderFragment
import ru.easycode.zerotoheroandroidtdd.folders.list.FolderListFragment
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteFragment
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteFragment

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).navigation.updateManager(supportFragmentManager)

        val viewModel = viewModel(MainViewModel::class.java)
        viewModel.init(savedInstanceState == null)
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
        return (application as ProvideViewModel).viewModel(clasz)
    }
}

interface Navigation {

    interface Update {
        fun update(screen: Screen)
    }

    interface Mutable : Update

    interface UpdateManager {
        fun updateManager(manager: FragmentManager)
    }

    interface All : Mutable, UpdateManager
    class Base : All {

        private var fragmentManager: FragmentManager? = null

        override fun update(screen: Screen) {
            fragmentManager?.let { screen.show(R.id.container, it) }
        }

        override fun updateManager(manager: FragmentManager) {
            fragmentManager = manager
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


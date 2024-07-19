package ru.easycode.zerotoheroandroidtdd.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.core.di.ProvideViewModel
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

        val viewModel = viewModel(MainViewModel::class.java)
        viewModel.init(savedInstanceState == null)

        viewModel.navigationLiveData().observe(this) { screen ->
            screen.show(R.id.container, supportFragmentManager)
        }
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
        return (application as ProvideViewModel).viewModel(clasz)
    }
}


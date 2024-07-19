package ru.easycode.zerotoheroandroidtdd.folders.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentFolderDetailsBinding
import ru.easycode.zerotoheroandroidtdd.core.di.ProvideViewModel

class FolderDetailsFragment : Fragment() {

    private var _binding: FragmentFolderDetailsBinding? = null
    private val binding: FragmentFolderDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentFolderDetailsBinding == null")

    private lateinit var viewModel: FolderDetailsViewModel

    private val onBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFolderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteAdapter()
        binding.notesRecyclerView.adapter = adapter

        viewModel =
            (requireActivity() as ProvideViewModel).viewModel(FolderDetailsViewModel::class.java)
        viewModel.init()

        adapter.itemClickListener = {
            viewModel.editNote(it)
        }

        binding.editFolderButton.setOnClickListener {
            viewModel.editFolder()
        }

        binding.addNoteButton.setOnClickListener {
            viewModel.createNote()
        }

        viewModel.liveDataListNote().observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        viewModel.liveDataFolderInfo().observe(viewLifecycleOwner) {
            binding.folderNameTextView.text = it.title
            binding.notesCountTextView.text = it.notesCount.toString()
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        onBackPressedCallback.remove()
    }
}
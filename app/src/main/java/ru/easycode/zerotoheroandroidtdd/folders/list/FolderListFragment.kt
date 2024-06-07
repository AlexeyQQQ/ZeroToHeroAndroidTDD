package ru.easycode.zerotoheroandroidtdd.folders.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentFolderListBinding
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.folders.details.NoteAdapter

class FolderListFragment : Fragment() {

    private var _binding: FragmentFolderListBinding? = null
    private val binding: FragmentFolderListBinding
        get() = _binding ?: throw RuntimeException("FragmentFolderListBinding == null")

    private lateinit var viewModel: FolderListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFolderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FolderAdapter()
        binding.foldersRecyclerView.adapter = adapter

        viewModel =
            (requireActivity() as ProvideViewModel).viewModel(FolderListViewModel::class.java)
        viewModel.init()

        adapter.itemClickListener = {
            viewModel.folderDetails(it)
        }

        binding.addButton.setOnClickListener {
            viewModel.addFolder()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
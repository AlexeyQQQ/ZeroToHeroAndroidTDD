package ru.easycode.zerotoheroandroidtdd.folders.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentEditFolderBinding
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel

class EditFolderFragment : Fragment() {

    private var _binding: FragmentEditFolderBinding? = null
    private val binding: FragmentEditFolderBinding
        get() = _binding ?: throw RuntimeException("FragmentCreateFolderBinding == null")

    private lateinit var viewModel: EditFolderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val folderId = requireArguments().getLong(EDIT_FOLDER_ID_KEY)

        viewModel =
            (requireActivity() as ProvideViewModel).viewModel(EditFolderViewModel::class.java)

        binding.saveFolderButton.setOnClickListener {
            viewModel.renameFolder(
                folderId,
                binding.folderEditText.text.toString()
            )
        }

        binding.deleteFolderButton.setOnClickListener {
            viewModel.deleteFolder(folderId)
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            binding.folderEditText.setText(it.title)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(folderId: Long): Fragment {
            return EditFolderFragment().apply {
                arguments = Bundle().apply {
                    putLong(EDIT_FOLDER_ID_KEY, folderId)
                }
            }
        }

        private const val EDIT_FOLDER_ID_KEY = "EDIT_FOLDER_ID_KEY"
    }
}
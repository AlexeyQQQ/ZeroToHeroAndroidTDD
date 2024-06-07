package ru.easycode.zerotoheroandroidtdd.folders.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentCreateFolderBinding
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel

class CreateFolderFragment : Fragment() {

    private var _binding: FragmentCreateFolderBinding? = null
    private val binding: FragmentCreateFolderBinding
        get() = _binding ?: throw RuntimeException("FragmentCreateFolderBinding == null")

    private lateinit var viewModel: CreateFolderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity() as ProvideViewModel).viewModel(CreateFolderViewModel::class.java)

        binding.saveFolderButton.setOnClickListener {
            viewModel.createFolder(
                binding.createFolderEditText.text.toString()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
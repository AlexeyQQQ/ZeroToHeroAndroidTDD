package ru.easycode.zerotoheroandroidtdd.note.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentCreateNoteBinding
import ru.easycode.zerotoheroandroidtdd.core.di.ProvideViewModel

class CreateNoteFragment : Fragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding: FragmentCreateNoteBinding
        get() = _binding ?: throw RuntimeException("FragmentCreateNoteBinding == null")

    private lateinit var viewModel: CreateNoteViewModel

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
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val folderId = requireArguments().getLong(CREATE_NOTE_FOLDER_ID_KEY)
        viewModel =
            (requireActivity() as ProvideViewModel).viewModel(CreateNoteViewModel::class.java)

        binding.saveNoteButton.setOnClickListener {
            viewModel.createNote(
                folderId,
                binding.createNoteEditText.text.toString()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        onBackPressedCallback.remove()
    }

    companion object {
        fun newInstance(folderId: Long): CreateNoteFragment {
            return CreateNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(CREATE_NOTE_FOLDER_ID_KEY, folderId)
                }
            }
        }

        private const val CREATE_NOTE_FOLDER_ID_KEY = "CREATE_NOTE_FOLDER_ID_KEY"
    }
}
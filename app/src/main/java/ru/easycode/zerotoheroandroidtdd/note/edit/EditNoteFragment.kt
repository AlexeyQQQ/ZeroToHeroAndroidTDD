package ru.easycode.zerotoheroandroidtdd.note.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentEditNoteBinding
import ru.easycode.zerotoheroandroidtdd.core.di.ProvideViewModel

class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding: FragmentEditNoteBinding
        get() = _binding ?: throw RuntimeException("FragmentEditNoteBinding == null")

    private lateinit var viewModel: EditNoteViewModel

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
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = requireArguments().getLong(EDIT_NOTE_ID_KEY)

        viewModel = (requireActivity() as ProvideViewModel).viewModel(EditNoteViewModel::class.java)
        viewModel.init(noteId)

        binding.saveNoteButton.setOnClickListener {
            viewModel.renameNote(
                noteId,
                binding.noteEditText.text.toString()
            )
        }

        binding.deleteNoteButton.setOnClickListener {
            viewModel.deleteNote(noteId)
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            binding.noteEditText.setText(it)
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        onBackPressedCallback.remove()
    }

    companion object {
        fun newInstance(noteId: Long): EditNoteFragment {
            return EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(EDIT_NOTE_ID_KEY, noteId)
                }
            }
        }

        private const val EDIT_NOTE_ID_KEY = "EDIT_NOTE_ID_KEY"
    }
}
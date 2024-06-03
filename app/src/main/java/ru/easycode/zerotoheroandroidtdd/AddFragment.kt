package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentAddBinding
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding
        get() = _binding ?: throw RuntimeException("FragmentAddBinding == null")

    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).viewModel(AddViewModel::class.java)

        binding.saveButton.setOnClickListener {
            val text = binding.addInputEditText.text.toString()
            viewModel.add(text)
            (requireActivity() as AddNavigation).navigateFromAdd()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

interface AddNavigation {
    fun navigateFromAdd()
}

object AddScreen : Screen.ReplaceAndAddBackStack() {
    override fun fragment(): Fragment = AddFragment()
}
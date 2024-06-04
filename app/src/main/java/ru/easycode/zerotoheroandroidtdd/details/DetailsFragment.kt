package ru.easycode.zerotoheroandroidtdd.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.Screen
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentDetailsBinding
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.repository.ItemUi

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailsBinding == null")

    private lateinit var viewModel: DetailsViewModel
    private lateinit var item: ItemUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = requireArguments().getSerializable(ITEM_KEY) as ItemUi
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).viewModel(DetailsViewModel::class.java)


        binding.itemTextView.text = item.text
        binding.itemInputEditText.setText(item.text)
        viewModel.init(item.id)

        binding.updateButton.setOnClickListener {
            viewModel.update(item.id, binding.itemInputEditText.text.toString())
            (requireActivity() as DetailsNavigation).navigateFromDetails()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.delete(item.id)
            (requireActivity() as DetailsNavigation).navigateFromDetails()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ITEM_KEY = "ITEM_KEY"
    }
}

interface DetailsNavigation {
    fun navigateFromDetails()
}

data class DetailsScreen(private val bundle: Bundle) : Screen.ReplaceAndAddToBackstack() {
    override fun fragment(): Fragment = DetailsFragment().apply {
        arguments = bundle
    }
}
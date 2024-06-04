package ru.easycode.zerotoheroandroidtdd.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.Screen
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentListBinding
import ru.easycode.zerotoheroandroidtdd.details.DetailsFragment.Companion.ITEM_KEY
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding ?: throw RuntimeException("FragmentListBinding == null")

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemAdapter()
        binding.recyclerView.adapter = adapter

        viewModel = (requireActivity() as ProvideViewModel).viewModel(ListViewModel::class.java)
        viewModel.init()

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        binding.addButton.setOnClickListener {
            (requireActivity() as ListNavigation).navigateFromListToAdd()
        }

        adapter.itemClickListener = {
            (requireActivity() as ListNavigation).navigateFromListToDetails(
                Bundle().apply { putSerializable(ITEM_KEY, it) }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface ListNavigation {
    fun navigateFromListToAdd()
    fun navigateFromListToDetails(bundle: Bundle)
}

object ListScreen : Screen.Replace() {
    override fun fragment(): Fragment = ListFragment()
}
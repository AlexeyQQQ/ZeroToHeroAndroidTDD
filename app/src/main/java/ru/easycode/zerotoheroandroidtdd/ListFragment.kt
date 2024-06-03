package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.adapter.ItemAdapter
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentMainBinding
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
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
            (requireActivity() as ListNavigation).navigateFromList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface ListNavigation {
    fun navigateFromList()
}

object ListScreen : Screen.Replace() {
    override fun fragment(): Fragment = ListFragment()
}
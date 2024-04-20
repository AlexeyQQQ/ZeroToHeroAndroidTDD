package ru.easycode.zerotoheroandroidtdd.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class AbstractFragment<B : ViewBinding> : Fragment() {

    protected var _binding: B? = null
    protected val binding: B
        get() = _binding ?: throw RuntimeException("Binding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bind(inflater, container)
        return binding.root
    }

    protected abstract fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): B

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
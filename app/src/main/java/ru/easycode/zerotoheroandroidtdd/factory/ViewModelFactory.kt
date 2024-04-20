package ru.easycode.zerotoheroandroidtdd.factory

import androidx.lifecycle.ViewModel

interface ViewModelFactory : ProvideViewModel, ClearViewModel {

    class Base(
        private val provideViewModel: ProvideViewModel,
    ) : ViewModelFactory {

        private val map = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return if (map.contains(viewModelClass)) {
                map[viewModelClass] as T
            } else {
                val viewModel = provideViewModel.viewModel(viewModelClass)
                map[viewModelClass] = viewModel
                viewModel
            }
        }

        override fun clear(viewModelClass: Class<out ViewModel>) {
            map.remove(viewModelClass)
        }
    }
}
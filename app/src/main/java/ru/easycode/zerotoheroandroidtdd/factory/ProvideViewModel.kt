package ru.easycode.zerotoheroandroidtdd.factory

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.create.CreateViewModel
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.list.ListViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel
import ru.easycode.zerotoheroandroidtdd.navigation.Navigation

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base(
        private val clearViewModel: ClearViewModel,
    ) : ProvideViewModel {

        private val navigation = Navigation.Base()
        private val sharedLiveData: ListLiveDataWrapper.All = ListLiveDataWrapper.Base()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                MainViewModel::class.java -> MainViewModel(navigation)
                ListViewModel::class.java -> ListViewModel(sharedLiveData, navigation)
                CreateViewModel::class.java -> CreateViewModel(sharedLiveData, navigation, clearViewModel)
                else -> throw IllegalStateException("Unknown viewModelClass $viewModelClass")
            } as T
        }
    }
}
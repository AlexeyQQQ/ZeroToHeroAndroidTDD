package ru.easycode.zerotoheroandroidtdd.di

import android.app.Application
import androidx.lifecycle.ViewModel

class App : Application(), ProvideViewModel {

    private lateinit var factory: ViewModelFactory

    private val clear: ClearViewModel = object : ClearViewModel {
        override fun clearViewModel(clasz: Class<out ViewModel>) {
            factory.clearViewModel(clasz)
        }
    }

    override fun onCreate() {
        super.onCreate()
        factory = ViewModelFactory.Base(ProvideViewModel.Base(clear, this))
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return factory.viewModel(viewModelClass)
    }
}
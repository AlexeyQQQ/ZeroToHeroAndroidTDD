package ru.easycode.zerotoheroandroidtdd.core

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.di.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.di.Core
import ru.easycode.zerotoheroandroidtdd.core.di.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.core.di.ViewModelFactory

class App : Application(), ProvideViewModel {

    private lateinit var factory: ViewModelFactory
    private lateinit var core: Core

    private val clear: ClearViewModels = object : ClearViewModels {
        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            factory.clear(*viewModelClasses)
        }
    }

    override fun onCreate() {
        super.onCreate()
        core = Core(this)
        factory = ProvideViewModel.Factory(ProvideViewModel.Base(clear, core))
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
        return factory.viewModel(clasz)
    }
}
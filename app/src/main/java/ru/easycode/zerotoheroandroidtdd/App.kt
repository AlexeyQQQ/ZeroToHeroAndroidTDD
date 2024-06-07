package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.di.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.di.Core
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.di.ViewModelFactory
import ru.easycode.zerotoheroandroidtdd.main.Navigation

class App : Application(), ProvideViewModel {

    private lateinit var factory: ViewModelFactory
    private lateinit var core: Core
    val navigation = Navigation.Base()

    private val clear: ClearViewModels = object : ClearViewModels {
        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            factory.clear(*viewModelClasses)
        }
    }

    override fun onCreate() {
        super.onCreate()
        core = Core(this, navigation)
        factory = ProvideViewModel.Factory(ProvideViewModel.Base(clear, core))
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
        return factory.viewModel(clasz)
    }
}
package ru.easycode.zerotoheroandroidtdd.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import ru.easycode.zerotoheroandroidtdd.AddViewModel
import ru.easycode.zerotoheroandroidtdd.room.Database
import ru.easycode.zerotoheroandroidtdd.wrappers.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.ListViewModel
import ru.easycode.zerotoheroandroidtdd.Now
import ru.easycode.zerotoheroandroidtdd.Repository

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base(
        private val clearViewModel: ClearViewModel,
        private val context: Context,
    ) : ProvideViewModel {

        private val database by lazy {
            Room.databaseBuilder(
                context,
                Database::class.java,
                Database::class.java.simpleName,
            ).build()
        }

        private val repository = Repository.Base(
            dataSource = database.dao(),
            now = Now.Base(),
        )
        private val listLiveData = ListLiveDataWrapper.Base()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                ListViewModel::class.java -> ListViewModel(
                    repository = repository,
                    liveDataWrapper = listLiveData,
                    dispatcher = Dispatchers.IO,
                    dispatcherMain = Dispatchers.Main.immediate,
                )

                AddViewModel::class.java -> AddViewModel(
                    repository = repository,
                    liveDataWrapper = listLiveData,
                    clear = clearViewModel,
                    dispatcher = Dispatchers.IO,
                    dispatcherMain = Dispatchers.Main.immediate,
                )

                else -> throw IllegalStateException("Unknown viewModelClass $viewModelClass")
            } as T
        }
    }
}
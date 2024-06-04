package ru.easycode.zerotoheroandroidtdd.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ru.easycode.zerotoheroandroidtdd.repository.Now
import ru.easycode.zerotoheroandroidtdd.repository.Repository
import ru.easycode.zerotoheroandroidtdd.add.AddViewModel
import ru.easycode.zerotoheroandroidtdd.details.DetailsViewModel
import ru.easycode.zerotoheroandroidtdd.list.ListViewModel
import ru.easycode.zerotoheroandroidtdd.room.Database
import ru.easycode.zerotoheroandroidtdd.wrappers.ListLiveDataWrapper

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
                )

                AddViewModel::class.java -> AddViewModel(
                    repository = repository,
                    liveDataWrapper = listLiveData,
                    clear = clearViewModel,
                )

                DetailsViewModel::class.java -> DetailsViewModel(
                    changeLiveDataWrapper = listLiveData,
                    repository = repository,
                    clear = clearViewModel,
                )

                else -> throw IllegalStateException("Unknown viewModelClass $viewModelClass")
            } as T
        }
    }
}
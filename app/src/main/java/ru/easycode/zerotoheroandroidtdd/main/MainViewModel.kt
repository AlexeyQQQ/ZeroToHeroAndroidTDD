package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.core.Navigation

class MainViewModel(
    private val navigation: Navigation.Mutable,
) : ViewModel() {

    fun init(firstRun: Boolean) {
        if (firstRun) navigation.update(FoldersListScreen)
    }

    fun navigationLiveData() = navigation.liveData()
}
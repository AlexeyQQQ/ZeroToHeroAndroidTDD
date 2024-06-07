package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val navigation: Navigation.Mutable,
) : ViewModel() {

    fun init(firstRun: Boolean) {
        if (firstRun) navigation.update(FoldersListScreen)
    }
}
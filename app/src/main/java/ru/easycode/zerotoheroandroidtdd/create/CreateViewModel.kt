package ru.easycode.zerotoheroandroidtdd.create

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.factory.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.navigation.Navigation
import ru.easycode.zerotoheroandroidtdd.navigation.Screen

class CreateViewModel(
    private val addLiveDataWrapper: ListLiveDataWrapper.Add,
    private val navigation: Navigation.Update,
    private val clearViewModel: ClearViewModel,
) : ViewModel() {

    fun add(text: CharSequence) {
        addLiveDataWrapper.add(text)
        comeback()
    }

    fun comeback() {
        navigation.update(Screen.Pop)
        clearViewModel.clear(CreateViewModel::class.java)
    }
}
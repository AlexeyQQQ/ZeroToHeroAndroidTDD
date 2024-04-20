package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.navigation.ListScreen
import ru.easycode.zerotoheroandroidtdd.navigation.Navigation
import ru.easycode.zerotoheroandroidtdd.navigation.Screen

class MainViewModel(
    private val navigation: Navigation.Mutable,
) : Navigation.Read, ViewModel() {

    fun init(firstRun: Boolean) {
        if (firstRun) {
            navigation.update(ListScreen)
        }
    }

    override fun liveData(): LiveData<Screen> {
        return navigation.liveData()
    }

}

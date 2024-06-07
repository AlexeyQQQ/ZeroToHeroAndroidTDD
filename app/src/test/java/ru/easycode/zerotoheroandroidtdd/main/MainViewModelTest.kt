package ru.easycode.zerotoheroandroidtdd.main

import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.Order

class MainViewModelTest {

    @Test
    fun test() {
        val order = Order()
        val navigation: FakeNavigation.Mutable = FakeNavigation.Base(order)
        val viewModel = MainViewModel(
            navigation = navigation
        )

        viewModel.init(firstRun = true)
        navigation.checkScreen(FoldersListScreen)
    }
}
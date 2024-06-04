package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.Dispatchers
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.list.ListViewModel
import ru.easycode.zerotoheroandroidtdd.repository.ItemUi
import ru.easycode.zerotoheroandroidtdd.repository.Repository

class ListViewModelTest {

    @Test
    fun test() {
        val repository = FakeRepository.Base()
        val liveDataWrapper = FakeListLiveDataWrapper.Base()
        val viewModel = ListViewModel(
            repository = repository,
            liveDataWrapper = liveDataWrapper,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )

        repository.expectList(listOf(ItemUi(id = 0L, text = "1"), ItemUi(id = 1L, text = "2")))

        viewModel.init()

        liveDataWrapper.checkUpdateCallList(
            listOf(
                ItemUi(id = 0L, text = "1"),
                ItemUi(id = 1L, text = "2")
            )
        )
    }
}

private interface FakeRepository : Repository.Read {

    fun expectList(list: List<ItemUi>)

    class Base : FakeRepository {

        private val list = mutableListOf<ItemUi>()

        override fun expectList(list: List<ItemUi>) {
            this.list.addAll(list)
        }

        override fun list(): List<ItemUi> {
            return list
        }
    }
}
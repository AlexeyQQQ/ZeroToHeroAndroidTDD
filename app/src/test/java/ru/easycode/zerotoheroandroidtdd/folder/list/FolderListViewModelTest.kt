package ru.easycode.zerotoheroandroidtdd.folder.list

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.data.Folder
import ru.easycode.zerotoheroandroidtdd.core.data.FolderUi
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation.Companion.NAVIGATE
import ru.easycode.zerotoheroandroidtdd.core.Order
import ru.easycode.zerotoheroandroidtdd.folder.list.FakeListLiveDataWrapper.Companion.UPDATE
import ru.easycode.zerotoheroandroidtdd.folder.list.FakeRepository.Companion.FOLDERS
import ru.easycode.zerotoheroandroidtdd.core.data.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folders.list.FolderListViewModel
import ru.easycode.zerotoheroandroidtdd.core.CreateFolderScreen
import ru.easycode.zerotoheroandroidtdd.core.FolderDetailsScreen
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.core.wrappers.FolderLiveDataWrapper

class FolderListViewModelTest {

    private lateinit var order: Order
    private lateinit var navigation: FakeNavigation.Update
    private lateinit var repository: FakeRepository
    private lateinit var folderLiveDataWrapper: FakeFolderUpdateLiveDataWrapper
    private lateinit var listLiveDataWrapper: FakeListLiveDataWrapper
    private lateinit var viewModel: FolderListViewModel

    @Before
    fun setup() {
        order = Order()
        repository = FakeRepository.Base(order)
        listLiveDataWrapper = FakeListLiveDataWrapper.Base(order)
        folderLiveDataWrapper = FakeFolderUpdateLiveDataWrapper.Base(order)
        navigation = FakeNavigation.Base(order)
        viewModel = FolderListViewModel(
            repository = repository,
            listLiveDataWrapper = listLiveDataWrapper,
            folderLiveDataWrapper = folderLiveDataWrapper,
            navigation = navigation,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_init() {
        repository.expectList(
            listOf(
                Folder(id = 1L, title = "first folder", notesCount = 1),
                Folder(id = 2L, title = "second folder", notesCount = 0),
            )
        )

        viewModel.init()
        listLiveDataWrapper.checkList(
            listOf(
                FolderUi(id = 1L, title = "first folder", notesCount = 1),
                FolderUi(id = 2L, title = "second folder", notesCount = 0),
            )
        )
        order.check(listOf(FOLDERS, UPDATE))
    }

    @Test
    fun test_add() {
        viewModel.addFolder()

        navigation.checkScreen(CreateFolderScreen)
        order.check(listOf(NAVIGATE))
    }

    @Test
    fun test_details() {
        repository.expectList(
            listOf(
                Folder(id = 1L, title = "first folder", notesCount = 1),
                Folder(id = 2L, title = "second folder", notesCount = 0),
            )
        )

        viewModel.init()
        listLiveDataWrapper.checkList(
            listOf(
                FolderUi(id = 1L, title = "first folder", notesCount = 1),
                FolderUi(id = 2L, title = "second folder", notesCount = 0),
            )
        )
        order.check(listOf(FOLDERS, UPDATE))

        viewModel.folderDetails(
            folderUi = FolderUi(
                id = 1L,
                title = "first folder",
                notesCount = 1
            )
        )
        folderLiveDataWrapper.check(
            FolderUi(
                id = 1L,
                title = "first folder",
                notesCount = 1
            )
        )
        navigation.checkScreen(FolderDetailsScreen)
        order.check(listOf(FOLDERS, UPDATE, UPDATE_FOLDER_LIVEDATA, NAVIGATE))
    }
}

private interface FakeListLiveDataWrapper : FolderListLiveDataWrapper.UpdateListAndRead {

    companion object {
        const val UPDATE = "FolderListLiveDataWrapper#update"
    }

    fun checkList(expected: List<FolderUi>)

    class Base(private val order: Order) : FakeListLiveDataWrapper {

        private val actual = mutableListOf<FolderUi>()

        override fun checkList(expected: List<FolderUi>) {
            assertEquals(expected, actual)
        }

        override fun update(list: List<FolderUi>) {
            actual.clear()
            actual.addAll(list)
            order.add(UPDATE)
        }
    }
}

private interface FakeRepository : FoldersRepository.ReadList {

    fun expectList(folders: List<Folder>)

    companion object {
        const val FOLDERS = "FoldersRepository#folders"
    }

    class Base(private val order: Order) : FakeRepository {

        private val list = mutableListOf<Folder>()

        override fun expectList(folders: List<Folder>) {
            list.clear()
            list.addAll(folders)
        }

        override suspend fun folders(): List<Folder> {
            order.add(FOLDERS)
            return list
        }
    }
}

private interface FakeFolderUpdateLiveDataWrapper : FolderLiveDataWrapper.Update {

    fun check(expected: FolderUi)

    class Base(private val order: Order) : FakeFolderUpdateLiveDataWrapper {

        private lateinit var actual: FolderUi

        override fun check(expected: FolderUi) {
            assertEquals(expected, actual)
        }

        override fun update(folder: FolderUi) {
            order.add(UPDATE_FOLDER_LIVEDATA)
            actual = folder
        }
    }
}

private const val UPDATE_FOLDER_LIVEDATA = "FolderLiveDataWrapper.Update#update"

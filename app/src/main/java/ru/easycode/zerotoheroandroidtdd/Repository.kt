package ru.easycode.zerotoheroandroidtdd

import ru.easycode.zerotoheroandroidtdd.room.ItemCache
import ru.easycode.zerotoheroandroidtdd.room.ItemsDao

interface Repository {

    interface Read : Repository {
        fun list(): List<String>
    }

    interface Add : Repository {
        fun add(value: String)
    }

    interface Mutable : Read, Add

    class Base(
        private val dataSource: ItemsDao,
        private val now: Now,
    ) : Mutable {

        override fun list(): List<String> = dataSource.list().map { it.text }

        override fun add(value: String) {
            dataSource.add(
                ItemCache(now.nowMillis(), value)
            )
        }
    }
}

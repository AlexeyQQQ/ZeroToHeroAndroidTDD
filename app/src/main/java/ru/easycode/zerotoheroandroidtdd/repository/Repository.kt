package ru.easycode.zerotoheroandroidtdd.repository

import ru.easycode.zerotoheroandroidtdd.room.ItemCache
import ru.easycode.zerotoheroandroidtdd.room.ItemsDao

interface Repository {

    interface Read {
        fun list(): List<ItemUi>
    }


    interface Add {
        fun add(value: String): Long
    }

    interface GetItem {
        fun item(id: Long): ItemUi
    }

    interface Delete {
        fun delete(id: Long)
    }

    interface Update {
        fun update(id: Long, newText: String)
    }

    interface Change : GetItem, Delete, Update

    interface All : Add, Read, Change

    class Base(
        private val dataSource: ItemsDao,
        private val now: Now,
    ) : All {

        override fun add(value: String): Long {
            val nowTime = now.nowMillis()
            dataSource.add(ItemCache(nowTime, value))
            return nowTime
        }

        override fun list(): List<ItemUi> {
            return dataSource.list().map {
                ItemUi(it.id, it.text)
            }
        }

        override fun item(id: Long): ItemUi {
            val itemCache = dataSource.item(id)
            return ItemUi(itemCache.id, itemCache.text)
        }

        override fun update(id: Long, newText: String) {
            dataSource.add(ItemCache(id, newText))
        }

        override fun delete(id: Long) {
            dataSource.delete(id)
        }
    }
}
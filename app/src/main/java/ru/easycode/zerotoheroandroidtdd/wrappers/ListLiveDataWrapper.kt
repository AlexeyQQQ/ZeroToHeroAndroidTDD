package ru.easycode.zerotoheroandroidtdd.wrappers

interface ListLiveDataWrapper {

    interface Read : LiveDataWrapper.Read<List<String>>

    interface Update : LiveDataWrapper.Update<List<String>>

    interface Mutable : Read, Update

    interface Add {
        fun add(value: String)
    }

    interface All : Mutable, Add


    class Base : LiveDataWrapper.Abstract<List<String>>(), All {

        override fun add(value: String) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            list.add(value)
            update(list)
        }
    }
}

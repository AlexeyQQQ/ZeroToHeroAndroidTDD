package ru.easycode.zerotoheroandroidtdd

interface Now {

    fun timeInMillis(): Long

    class Base : Now {

        override fun timeInMillis(): Long {
            return System.currentTimeMillis()
        }
    }
}
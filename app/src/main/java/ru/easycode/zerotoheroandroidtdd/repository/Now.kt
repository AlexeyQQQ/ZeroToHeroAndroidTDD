package ru.easycode.zerotoheroandroidtdd.repository

interface Now {

    fun nowMillis(): Long

    class Base : Now {

        override fun nowMillis(): Long {
            return System.currentTimeMillis()
        }
    }
}
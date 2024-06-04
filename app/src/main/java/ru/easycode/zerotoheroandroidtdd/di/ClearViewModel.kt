package ru.easycode.zerotoheroandroidtdd.di

import androidx.lifecycle.ViewModel

interface ClearViewModel {

    fun clearViewModel(clasz: Class<out ViewModel>)
}
package ru.easycode.zerotoheroandroidtdd.di

import androidx.lifecycle.ViewModel

interface ClearViewModels {

    fun clear(vararg viewModelClasses: Class<out ViewModel>)
}
package ru.easycode.zerotoheroandroidtdd.core.di

import androidx.lifecycle.ViewModel

interface ClearViewModels {

    fun clear(vararg viewModelClasses: Class<out ViewModel>)
}
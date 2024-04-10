package ru.easycode.zerotoheroandroidtdd.factory

import androidx.lifecycle.ViewModel

interface ClearViewModel {

    fun clear(viewModelClass: Class<out ViewModel>)
}
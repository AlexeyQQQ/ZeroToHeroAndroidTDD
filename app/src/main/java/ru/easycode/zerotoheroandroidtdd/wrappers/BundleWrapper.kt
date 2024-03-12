package ru.easycode.zerotoheroandroidtdd.wrappers

import android.os.Bundle
import ru.easycode.zerotoheroandroidtdd.UiState

interface BundleWrapper {

    interface Save : BundleWrapper {
        fun save(uiState: UiState)
    }

    interface Restore : BundleWrapper {
        fun restore(): UiState
    }

    interface Mutable : Save, Restore


    class Base(
        private val bundle: Bundle,
    ) : Mutable {

        override fun save(uiState: UiState) {
            bundle.putSerializable(KEY_BUNDLE, uiState)
        }

        override fun restore(): UiState {
            return bundle.getSerializable(KEY_BUNDLE) as UiState
        }

        companion object {
            private const val KEY_BUNDLE = "key_bundle"
        }
    }
}

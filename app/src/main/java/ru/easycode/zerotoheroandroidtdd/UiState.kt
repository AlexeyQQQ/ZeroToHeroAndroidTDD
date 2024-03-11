package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.io.Serializable

interface UiState : Serializable {

    operator fun invoke(
        progressBar: ProgressBar,
        textView: TextView,
        button: Button,
    )


    object ShowProgress : UiState {
        override fun invoke(
            progressBar: ProgressBar,
            textView: TextView,
            button: Button,
        ) {
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.GONE
            button.isEnabled = false
        }
    }

    object ShowData : UiState {
        override fun invoke(
            progressBar: ProgressBar,
            textView: TextView,
            button: Button,
        ) {
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
            button.isEnabled = true
        }
    }
}
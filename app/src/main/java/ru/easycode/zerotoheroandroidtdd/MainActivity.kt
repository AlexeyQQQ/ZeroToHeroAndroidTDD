package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var state: State = State.Initial

    private lateinit var rootLayout: LinearLayout
    private lateinit var titleTextView: TextView
    private lateinit var hideButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById((R.id.rootLayout))
        titleTextView = findViewById(R.id.titleTextView)
        hideButton = findViewById(R.id.removeButton)

        hideButton.setOnClickListener {
            state = State.Removed
            state.apply(rootLayout, titleTextView, hideButton)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState.getSerializable(KEY) as State
        state.apply(rootLayout, titleTextView, hideButton)
    }

    companion object {
        private const val KEY = "key_removed"
    }
}


interface State : Serializable {

    fun apply(
        layout: LinearLayout,
        textView: TextView,
        button: Button,
    )

    object Initial : State {
        override fun apply(
            layout: LinearLayout,
            textView: TextView,
            button: Button,
        ) = Unit
    }

    object Removed : State {
        override fun apply(
            layout: LinearLayout,
            textView: TextView,
            button: Button,
        ) {
            layout.removeView(textView)
            button.isEnabled = false
        }
    }

}

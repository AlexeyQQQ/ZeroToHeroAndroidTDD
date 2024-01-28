package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var rootLayout: LinearLayout
    private lateinit var countTextView: TextView
    private lateinit var incrementButton: Button

    private val count: Count = Count.Base(2, 4)
    private var uiState: UiState = UiState.Base("0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.rootLayout)
        countTextView = findViewById(R.id.countTextView)
        incrementButton = findViewById(R.id.incrementButton)

        incrementButton.setOnClickListener {
            val number = countTextView.text.toString()
            uiState = count.increment(number)
            uiState.apply(countTextView, incrementButton)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_UI_STATE, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        uiState = savedInstanceState.getSerializable(KEY_UI_STATE) as UiState
        uiState.apply(countTextView, incrementButton)
    }

    companion object {
        private const val KEY_UI_STATE = "saved_ui_state"
    }
}


interface Count {

    fun increment(number: String): UiState

    class Base(
        private val step: Int,
        private val max: Int,
    ) : Count {

        init {
            if (step <= 0) throw IllegalStateException("step should be positive, but was $step")
            if (max <= 0) throw IllegalStateException("max should be positive, but was $max")
            if (max < step) throw IllegalStateException("max should be more than step")
        }

        override fun increment(number: String): UiState {
            val num = number.toInt()
            val result = num + step

            return if (result + step <= max) UiState.Base(result.toString())
            else if (result + step > max) UiState.Max(result.toString())
            else UiState.Max(number)
        }
    }
}


interface UiState : Serializable {

    fun apply(textView: TextView, button: Button)

    data class Base(
        private val text: String,
    ) : UiState {

        override fun apply(textView: TextView, button: Button) {
            textView.text = text
        }
    }

    data class Max(
        private val text: String,
    ) : UiState {

        override fun apply(textView: TextView, button: Button) {
            textView.text = text
            button.isEnabled = false
        }
    }
}
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
    private lateinit var decrementButton: Button

    private val count: Count = Count.Base(2, 4, 0)
    private var state: UiState = count.initial("0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        savedInstanceState?.let {
            state = it.getSerializable(KEY_STATE) as UiState
        }
        state.apply(countTextView, incrementButton, decrementButton)

        incrementButton.setOnClickListener {
            val num = countTextView.text.toString()
            state = count.increment(num)
            state.apply(countTextView, incrementButton, decrementButton)
        }

        decrementButton.setOnClickListener {
            val num = countTextView.text.toString()
            state = count.decrement(num)
            state.apply(countTextView, incrementButton, decrementButton)
        }
    }

    private fun initViews() {
        rootLayout = findViewById(R.id.rootLayout)
        countTextView = findViewById(R.id.countTextView)
        incrementButton = findViewById(R.id.incrementButton)
        decrementButton = findViewById(R.id.decrementButton)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_STATE, state)
    }

    companion object {
        private const val KEY_STATE = "key_state"
    }
}


interface Count {

    fun initial(number: String): UiState

    fun increment(number: String): UiState

    fun decrement(number: String): UiState

    class Base(
        private val step: Int,
        private val max: Int,
        private val min: Int,
    ) : Count {

        init {
            if (step <= 0) throw IllegalStateException("step should be positive, but was $step")
            if (max <= 0) throw IllegalStateException("max should be positive, but was $max")
            if (max < step) throw IllegalStateException("max should be more than step")
            if (max < min) throw IllegalStateException("max should be more than min")
        }

        override fun initial(number: String): UiState {
            return when (number.toInt()) {
                max -> UiState.Max(number)
                min -> UiState.Min(number)
                else -> UiState.Base(number)
            }
        }

        override fun increment(number: String): UiState {
            val num = number.toInt()
            if (num + step > max) return UiState.Max(max.toString())

            val result = num + step
            return if (result + step <= max) UiState.Base(result.toString())
            else UiState.Max(result.toString())
        }

        override fun decrement(number: String): UiState {
            val num = number.toInt()
            if (num - step < min) return UiState.Min(min.toString())

            val result = num - step
            return if (result - step >= min) UiState.Base(result.toString())
            else UiState.Min(result.toString())
        }
    }
}


interface UiState : Serializable {

    fun apply(textView: TextView, incrementButton: Button, decrementButton: Button)

    data class Base(
        private val text: String,
    ) : UiState {

        override fun apply(
            textView: TextView,
            incrementButton: Button,
            decrementButton: Button,
        ) {
            textView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = true
        }
    }

    data class Max(
        private val text: String,
    ) : UiState {

        override fun apply(
            textView: TextView,
            incrementButton: Button,
            decrementButton: Button,
        ) {
            textView.text = text
            incrementButton.isEnabled = false
            decrementButton.isEnabled = true
        }
    }

    data class Min(
        private val text: String,
    ) : UiState {

        override fun apply(
            textView: TextView,
            incrementButton: Button,
            decrementButton: Button,
        ) {
            textView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = false
        }
    }
}







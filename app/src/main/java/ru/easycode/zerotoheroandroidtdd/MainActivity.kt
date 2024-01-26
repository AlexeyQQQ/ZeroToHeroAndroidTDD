package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var rootLayout: LinearLayout
    private lateinit var titleTextView: TextView
    private lateinit var hideButton: Button

    private val count = Count.Base(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById((R.id.rootLayout))
        titleTextView = findViewById(R.id.countTextView)
        hideButton = findViewById(R.id.incrementButton)

        hideButton.setOnClickListener {
            val oldNumber = titleTextView.text.toString()
            val newNumber = count.increment(oldNumber)
            titleTextView.text = newNumber
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_NUMBER, titleTextView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        titleTextView.text = savedInstanceState.getString(KEY_NUMBER)
    }

    companion object {
        private const val KEY_NUMBER = "key_number"
    }
}


interface Count {

    fun increment(number: String): String

    class Base(
        private val step: Int,
    ) : Count {

        init {
            if (step <= 0) {
                throw IllegalStateException("step should be positive, but was $step")
            }
        }

        override fun increment(number: String): String {
            val numberInt = number.toInt()
            return (numberInt + step).toString()
        }
    }
}
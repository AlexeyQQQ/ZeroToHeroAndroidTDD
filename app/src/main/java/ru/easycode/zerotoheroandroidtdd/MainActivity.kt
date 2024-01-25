package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains

class MainActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var hideButton: Button
    private lateinit var rootLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.titleTextView)
        hideButton = findViewById(R.id.removeButton)
        rootLayout = findViewById(R.id.rootLayout)

        hideButton.setOnClickListener {
            rootLayout.removeView(titleTextView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_TITLE, !rootLayout.contains(titleTextView))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getBoolean(KEY_TITLE)) {
            rootLayout.removeView(titleTextView)
        }
    }

    companion object {
        private const val KEY_TITLE = "title_does_not_exist"
    }
}
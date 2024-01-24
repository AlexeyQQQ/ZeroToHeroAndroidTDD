package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleTextView: TextView = findViewById(R.id.titleTextView)
        val changeButton: Button = findViewById(R.id.changeButton)

        changeButton.setOnClickListener {
            titleTextView.text = getString(R.string.i_am_an_android_developer)
        }
    }
}
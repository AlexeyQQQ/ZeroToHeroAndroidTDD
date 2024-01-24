package ru.easycode.zerotoheroandroidtdd

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var changeButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val saveText = sharedPreferences.getString("save_text", getString(R.string.hello_world))

        titleTextView = findViewById(R.id.titleTextView)
        changeButton = findViewById(R.id.changeButton)

        titleTextView.text = saveText

        changeButton.setOnClickListener {
            titleTextView.text = getString(R.string.i_am_an_android_developer)
            sharedPreferences.edit()
                .putString("save_text", titleTextView.text.toString())
                .apply()
        }
    }
}
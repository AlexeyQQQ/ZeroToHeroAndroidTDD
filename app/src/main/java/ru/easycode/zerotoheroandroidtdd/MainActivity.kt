package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val linearLayout = LinearLayout(this)
        val textView = TextView(this)
        textView.id = R.id.titleTextView
        textView.text = getString(R.string.tv_title_text)
        linearLayout.gravity = Gravity.CENTER
        linearLayout.addView(textView)
        setContentView(linearLayout)
    }
}
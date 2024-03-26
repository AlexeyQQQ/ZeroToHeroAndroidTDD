package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.databinding.ItemBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listOfTexts = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.actionButton.setOnClickListener {
            val inputText = binding.inputEditText.text.toString()
            addTextView(inputText)
            listOfTexts.add(inputText)
            binding.inputEditText.text?.clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(KEY, listOfTexts as ArrayList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        listOfTexts = savedInstanceState.getStringArrayList(KEY) ?: ArrayList()

        for (inputText in listOfTexts) {
            addTextView(inputText)
        }
    }

//    private fun addTextView(inputText: String) {
//        val textView = TextView(this).apply {
//            text = inputText
//        }
//        binding.contentLayout.addView(textView)
//    }

    private fun addTextView(inputText: String) {
        val itemBinding = ItemBinding.inflate(layoutInflater).apply {
            titleTextView.text = inputText
        }
        binding.contentLayout.addView(itemBinding.root)
    }

    companion object {
        private const val KEY = "key"
    }
}
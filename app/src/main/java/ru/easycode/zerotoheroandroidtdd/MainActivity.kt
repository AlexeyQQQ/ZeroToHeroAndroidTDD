package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionButton.setOnClickListener {
            load()
        }
    }

    private fun load() {
        binding.actionButton.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        Thread {
            Thread.sleep(2000)
            handler.post {
                binding.titleTextView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.actionButton.isEnabled = true
            }
        }.start()
    }
}
package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModelFactory by lazy {
        ViewModelFactory(LiveDataWrapper.Base(), Repository.Base())
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.liveData().observe(this) {
            it.invoke(
                binding.progressBar,
                binding.titleTextView,
                binding.actionButton,
            )
        }

        binding.actionButton.setOnClickListener {
            viewModel.load()
        }
    }
}
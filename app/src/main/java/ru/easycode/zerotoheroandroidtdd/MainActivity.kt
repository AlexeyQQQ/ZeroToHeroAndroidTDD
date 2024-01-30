package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val mainViewModelFactory = MainViewModelFactory(
        LiveDataWrapper.Base(),
        Repository.Base()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        viewModel.liveData().observe(this) {
            it.apply(binding.actionButton, binding.progressBar, binding.titleTextView)
        }

        binding.actionButton.setOnClickListener {
            viewModel.load()
        }
    }
}
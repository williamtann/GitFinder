package com.example.gitfinder

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.gitfinder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (!viewModel.keyword.isNullOrEmpty()) {
            binding.textView.text = "Searching with keyword: ${viewModel.keyword}"
        }

        binding.buttonSearch.setOnClickListener {
            val inputtedValue = binding.editText.text.toString().trim()
            binding.textView.text = "Searching with keyword: $inputtedValue"
            viewModel.keyword = inputtedValue
        }
    }
}

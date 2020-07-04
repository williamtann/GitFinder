package com.example.gitfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
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

        binding.buttonSearch.setOnClickListener {
            val inputtedValue = binding.editText.text.toString().trim()
            viewModel.updateKeyword(inputtedValue)
        }

        viewModel.keyword.observe(this, Observer { keyword ->
            binding.textView.text = "Searching with keyword: $keyword"
        })
    }
}

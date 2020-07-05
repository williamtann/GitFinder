package com.example.gitfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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

        viewModel.searchEnabled.observe(this, Observer { searchEnabled ->
            if (searchEnabled) {
                binding.textView.text = "Searching with keyword: ${viewModel.keyword.value}"
            } else {
                Toast.makeText(this, "Please input the search keyword", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.searchResult.observe(this, Observer { repoName ->
            binding.textView.text = "Repo found: $repoName"
        })

        viewModel.searchHistory.observe(this, Observer { searchHistory ->
            binding.textHistory.text = searchHistory
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.history) {
            binding.textHistory.visibility =
                if (binding.textHistory.visibility == View.GONE) View.VISIBLE
                else View.GONE
        }
        return super.onOptionsItemSelected(item)
    }
}

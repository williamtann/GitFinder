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
            val textInput = binding.editText.text.toString().trim()
            viewModel.search(textInput)
        }

        initObservation()
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

    private fun initObservation() {
        viewModel.searchEnabled.observe(this, Observer {
            if (it) {
                binding.textView.text = "Searching repo with keyword: ${viewModel.keyword.value}"
            } else {
                Toast.makeText(this, "Please input the search keyword", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.searchResult.observe(this, Observer {
            binding.textView.text = "Repo found: $it"
        })
        viewModel.searchHistory.observe(this, Observer {
            binding.textHistory.text = it
        })
    }
}

package com.example.gitfinder

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitfinder.adapter.RepoListAdapter
import com.example.gitfinder.databinding.ActivityMainBinding
import com.example.gitfinder.datamodel.Repo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: RepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        adapter = RepoListAdapter(this, object: RepoListAdapter.ItemClickListener {
            override fun onItemClicked(repo: Repo) {
                Toast.makeText(this@MainActivity, "Repo clicked: ${repo.name}", Toast.LENGTH_SHORT).show()
            }

        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.buttonSearch.setOnClickListener {
            val inputtedValue = binding.editText.text.toString().trim()
            viewModel.updateKeyword(inputtedValue)
        }

        viewModel.searchEnabled.observe(this, { searchEnabled ->
            binding.recyclerView.visibility = View.GONE
            binding.textView.visibility = View.VISIBLE
            if (searchEnabled) {
                binding.textView.text = "Searching with keyword: ${viewModel.keyword.value}"
            } else {
                Toast.makeText(this, "Please input the search keyword", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.repoFound.observe(this, { repoList ->
            if (repoList.isNotEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.textView.visibility = View.GONE
                adapter.data = repoList
                adapter.notifyDataSetChanged()
            } else {
                binding.textView.text = "No result found, please try with another keyword"
            }
        })

        viewModel.networkError.observe(this, { errorMessage ->
            binding.textView.visibility = View.GONE
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })

        viewModel.searchHistory.observe(this, { searchHistory ->
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

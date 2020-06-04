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
    private lateinit var listAdapter: RepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = RepoListAdapter(this, object : RepoListAdapter.ItemClickListener {

            override fun onItemClick(repo: Repo) {
                Toast.makeText(this@MainActivity, "Repo clicked: ${repo.name}", Toast.LENGTH_SHORT).show()
            }
        })
        binding.recyclerView.adapter = listAdapter
        binding.recyclerView.itemAnimator = null

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
                binding.recyclerView.visibility = View.GONE
                binding.textView.visibility = View.VISIBLE
                binding.textView.text = "Searching repo with keyword: ${viewModel.keyword.value}"
            } else {
                Toast.makeText(this, "Please input the search keyword", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.searchHistory.observe(this, Observer {
            binding.textHistory.text = it
        })
        viewModel.reposFound.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.textView.visibility = View.GONE
                listAdapter.submitList(it)
            } else {
                binding.textView.text = "No result found, please search with another keyword"
            }
        })
        viewModel.networkError.observe(this, Observer {
            binding.textView.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}

package com.example.gitfinder.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitfinder.R
import com.example.gitfinder.adapter.RepoListAdapter
import com.example.gitfinder.databinding.FragmentHomeBinding
import com.example.gitfinder.datamodel.Repo

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: RepoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        adapter = RepoListAdapter(requireContext(), object: RepoListAdapter.ItemClickListener {
            override fun onItemClicked(repo: Repo) {
                val action = HomeFragmentDirections.toRepoDetail(repo)
                findNavController().navigate(action)
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null

        binding.buttonSearch.setOnClickListener {
            val inputtedValue = binding.editText.text.toString().trim()
            viewModel.updateKeyword(inputtedValue)
        }

        viewModel.searchEnabled.observe(viewLifecycleOwner, Observer { searchEnabled ->
            binding.recyclerView.visibility = View.GONE
            binding.textView.visibility = View.VISIBLE
            if (searchEnabled) {
                binding.textView.text = "Searching with keyword: ${viewModel.keyword.value}"
            } else {
                Toast.makeText(context, "Please input the search keyword", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.repoFound.observe(viewLifecycleOwner, Observer { repoList ->
            if (repoList.isNotEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.textView.visibility = View.GONE
                adapter.submitList(repoList)
            } else {
                binding.textView.text = "No result found, please try with another keyword"
            }
        })

        viewModel.searchHistory.observe(viewLifecycleOwner, Observer { textHistory ->
            binding.textHistory.text = textHistory
        })

        viewModel.networkError.observe(viewLifecycleOwner, Observer { errorMessage ->
            binding.textView.visibility = View.GONE
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
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
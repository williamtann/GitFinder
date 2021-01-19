package com.example.gitfinder.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitfinder.adapter.RepoListAdapter
import com.example.gitfinder.databinding.FragmentBookmarkBinding
import com.example.gitfinder.datamodel.Repo

class BookmarkFragment: Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var viewModel: BookmarkViewModel
    private lateinit var adapter: RepoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BookmarkViewModel::class.java)

        adapter = RepoListAdapter(requireContext(), true, object: RepoListAdapter.ItemClickListener {
            override fun onItemClicked(repo: Repo) {
                val action = HomeFragmentDirections.toRepoDetail().setRepoData(repo).setFromCache(true)
                findNavController().navigate(action)
            }

            override fun onRemoveClicked(repo: Repo) {
                viewModel.removeRepo(repo)
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.pagedSavedRepo.observe(viewLifecycleOwner, Observer { repoList ->
            adapter.submitList(repoList)
        })
    }
}
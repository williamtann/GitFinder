package com.example.gitfinder.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.gitfinder.databinding.FragmentRepoDetailBinding
import com.example.gitfinder.datamodel.Repo

class RepoDetailFragment: Fragment() {

    private lateinit var binding: FragmentRepoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val repo = arguments?.get("repoData") as Repo
        val args: RepoDetailFragmentArgs by navArgs()
        val repo = args.repoData

        binding.textName.text = repo.name
        binding.textFullName.text = repo.fullName
        if (repo.description.isNullOrEmpty()) {
            binding.textDescription.visibility = View.GONE
        } else {
            binding.textDescription.visibility = View.VISIBLE
            binding.textDescription.text = repo.description
        }
    }
}
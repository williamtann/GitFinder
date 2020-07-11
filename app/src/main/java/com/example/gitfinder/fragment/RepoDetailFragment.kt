package com.example.gitfinder.fragment

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.gitfinder.databinding.FragmentRepoDetailBinding


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

        binding.textUrl.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.textUrl.text = repo.url
        binding.textUrl.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.url))
            startActivity(browserIntent)
        }

        binding.textStargazer.text = ": ${repo.stargazers}"
        binding.textWatcher.text = ": ${repo.watchers}"
    }
}
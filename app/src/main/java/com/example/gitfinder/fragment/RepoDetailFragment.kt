package com.example.gitfinder.fragment

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.gitfinder.databinding.FragmentRepoDetailBinding
import com.example.gitfinder.datamodel.Repo

class RepoDetailFragment: Fragment() {

    private lateinit var binding: FragmentRepoDetailBinding
    private lateinit var viewModel: RepoDetailViewModel

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

        viewModel = ViewModelProvider(this).get(RepoDetailViewModel::class.java)

        val args: RepoDetailFragmentArgs by navArgs()
        val repo = args.repoData
        val repoId = args.repoId
        val fromCache = args.fromCache

        if (repo != null) {
            updateView(repo, fromCache)
        } else if (viewModel.repoId.value == null) {
            viewModel.setRepoId(repoId)
        }

        viewModel.repoFetched.observe(viewLifecycleOwner, Observer { repoFetched ->
            updateView(repoFetched, fromCache)
        })
        viewModel.networkError.observe(viewLifecycleOwner, Observer { errorMessage ->
            binding.textInfo.text = errorMessage
        })
        viewModel.repoId.observe(viewLifecycleOwner, Observer {
            binding.textInfo.text = "Loading repo data.."
        })
    }

    private fun updateView(repo: Repo, fromCache: Boolean) {
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

        binding.layoutData.visibility = View.VISIBLE
        binding.textInfo.visibility = View.GONE

        binding.textNote.visibility = if (fromCache) View.VISIBLE else View.GONE
        binding.textNote.setText(repo.note)

        binding.buttonSave.setOnClickListener {
            if (fromCache) {
                viewModel.updateRepo(repo.id, binding.textNote.text.toString())
                Toast.makeText(context, "Repo Updated!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.saveRepo(repo)
                Toast.makeText(context, "Repo Saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
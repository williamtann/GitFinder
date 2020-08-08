package com.example.gitfinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitfinder.databinding.RepoListItemBinding
import com.example.gitfinder.datamodel.Repo

class RepoListAdapter(
    private val context: Context,
    private val listener: ItemClickListener
): RecyclerView.Adapter<RepoListAdapter.CustomViewHolder>() {

    var data: List<Repo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = RepoListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = holder.binding
        val repo = data[position]

        binding.textName.text = repo.name
        binding.textFullname.text = repo.fullName
        if (repo.description.isNullOrEmpty()) {
            binding.textDescription.visibility = View.GONE
        } else {
            binding.textDescription.visibility = View.VISIBLE
            binding.textDescription.text = repo.description
        }

        binding.root.setOnClickListener {
            listener.onItemClicked(repo)
        }
    }

    inner class CustomViewHolder(val binding: RepoListItemBinding): RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onItemClicked(repo: Repo)
    }
}
package com.example.gitfinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.gitfinder.databinding.RepoListItemBinding
import com.example.gitfinder.datamodel.Repo

class RepoListAdapter constructor(
    private val context: Context,
    private val listener: ItemClickListener
) : PagedListAdapter<Repo, RepoListAdapter.CustomViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = RepoListItemBinding.inflate(LayoutInflater.from(context))
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        getItem(position)?.also { repo ->
            val binding = holder.binding as RepoListItemBinding

            binding.textName.text = repo.name
            binding.textFullName.text = repo.fullName

            binding.root.setOnClickListener { listener.onItemClick(repo) }
        }
    }

    inner class CustomViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onItemClick(repo: Repo)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }
}
package com.example.gitfinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gitfinder.databinding.RepoListItemBinding
import com.example.gitfinder.datamodel.Repo

class RepoListAdapter(
    private val context: Context,
    private val showRemoveIcon: Boolean,
    private val listener: ItemClickListener
) : PagedListAdapter<Repo, RepoListAdapter.CustomViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = RepoListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = holder.binding
        val repo = getItem(position)

        if (repo != null) {
            binding.textName.text = repo.name
            binding.textFullName.text = repo.fullName
            if (repo.description.isNullOrEmpty()) {
                binding.textDescription.visibility = View.GONE
            } else {
                binding.textDescription.visibility = View.VISIBLE
                binding.textDescription.text = repo.description
            }

            binding.iconRemove.visibility = if (showRemoveIcon) View.VISIBLE else View.GONE
            binding.iconRemove.setOnClickListener {
                listener.onRemoveClicked(repo)
            }

            binding.root.setOnClickListener { listener.onItemClicked(repo) }
        }
    }

    inner class CustomViewHolder(val binding: RepoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onItemClicked(repo: Repo)
        fun onRemoveClicked(repo: Repo) {}
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }

        }
    }
}
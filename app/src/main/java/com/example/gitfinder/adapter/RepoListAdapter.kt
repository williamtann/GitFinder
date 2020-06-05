package com.example.gitfinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.gitfinder.databinding.RepoListItemBinding
import com.example.gitfinder.datamodel.Repo

class RepoListAdapter constructor(
    private val context: Context,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<RepoListAdapter.CustomViewHolder>() {

    var data: List<Repo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = RepoListItemBinding.inflate(LayoutInflater.from(context))
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = holder.binding as RepoListItemBinding
        val repo = data[position]

        binding.textName.text = repo.name
        binding.textFullName.text = repo.fullName
        if (repo.description.isNullOrEmpty()) {
            binding.textDescription.visibility = View.GONE
        } else {
            binding.textDescription.visibility = View.VISIBLE
            binding.textDescription.text = repo.description
        }

        binding.root.setOnClickListener { listener.onItemClick(repo) }
    }

    inner class CustomViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onItemClick(repo: Repo)
    }
}
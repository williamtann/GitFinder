package com.example.gitfinder.viewmodel

import androidx.lifecycle.LiveData
import com.example.gitfinder.datamodel.Repo

data class RepoSearchResult (
    val data: LiveData<List<Repo>>?,
    val networkError: LiveData<String>
)
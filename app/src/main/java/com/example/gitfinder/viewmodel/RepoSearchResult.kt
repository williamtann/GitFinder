package com.example.gitfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.gitfinder.datamodel.Repo

data class RepoSearchResult (
    val data: LiveData<PagedList<Repo>>,
    val networkError: LiveData<String>
)
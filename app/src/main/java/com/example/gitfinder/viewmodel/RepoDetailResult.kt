package com.example.gitfinder.viewmodel

import androidx.lifecycle.LiveData
import com.example.gitfinder.datamodel.Repo

data class RepoDetailResult (
    val data: LiveData<Repo>,
    val networkError: LiveData<String>
)
package com.example.gitfinder.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gitfinder.MainRepository
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.service.RemoteService
import com.example.gitfinder.viewmodel.RepoDetailResult

class RepoDetailViewModel: ViewModel() {

    private val repository = MainRepository(RemoteService.create())

    private val _repoId: MutableLiveData<Long> = MutableLiveData()
    val repoId: LiveData<Long>
        get() = _repoId

    private val repoDetailResult: LiveData<RepoDetailResult> = Transformations.map(_repoId) { repoId ->
        repository.getRepoById(repoId)
    }
    val repoFetched: LiveData<Repo> = Transformations.switchMap(repoDetailResult) { it.data }
    val networkError: LiveData<String> = Transformations.switchMap(repoDetailResult) { it.networkError }

    fun setRepoId(id: Long) {
        _repoId.value = id
    }
}
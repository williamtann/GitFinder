package com.example.gitfinder.fragment

import android.app.Application
import androidx.lifecycle.*
import com.example.gitfinder.MainRepository
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.service.RemoteService
import com.example.gitfinder.viewmodel.RepoDetailResult
import kotlinx.coroutines.launch

class RepoDetailViewModel(application: Application): AndroidViewModel(application) {

    private val repository = MainRepository(RemoteService.create(), application)

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

    fun saveRepo(repo: Repo) = viewModelScope.launch {
        repository.saveRepo(repo)
    }
}
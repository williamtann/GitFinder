package com.example.gitfinder

import androidx.lifecycle.*
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.model.RepoSearchResult
import com.example.gitfinder.service.RemoteService

class MainViewModel: ViewModel() {

    private var repository: MainRepository = MainRepository(RemoteService.create())

    private val _keyword = MutableLiveData<String>()
    val keyword: LiveData<String>
        get() = _keyword

    val searchEnabled: LiveData<Boolean> = Transformations.map(_keyword) { keyword -> !keyword.isNullOrEmpty() }
    val searchHistory = MediatorLiveData<String>()

    private val searchResult: LiveData<RepoSearchResult> = Transformations.map(_keyword) { repository.searchRepo(it) }
    val reposFound: LiveData<List<Repo>> = Transformations.switchMap(searchResult) { it.data }
    val networkError: LiveData<String> = Transformations.switchMap(searchResult) { it.networkError }

    init {
        searchHistory.value = "Search History:\n\n"
        searchHistory.addSource(_keyword) {
            searchHistory.value += "Keyword: $it\n"
        }
        searchHistory.addSource(reposFound) {
            searchHistory.value += "Repos Found: ${it.size}\n"
        }
    }

    fun search(keyword: String) {
        _keyword.value = keyword
    }
}
package com.example.gitfinder.fragment

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.gitfinder.MainRepository
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.service.RemoteService
import com.example.gitfinder.viewmodel.RepoSearchResult

class HomeViewModel: ViewModel() {

    private val repository = MainRepository(RemoteService.create())

    private val _keyword: MutableLiveData<String> = MutableLiveData()
    val keyword: LiveData<String>
        get() = _keyword

    fun updateKeyword(input: String) {
        _keyword.value = input
    }

    val searchEnabled: LiveData<Boolean> = Transformations.map(_keyword) { keyword ->
        !keyword.isNullOrEmpty()
    }

    private val searchResult: LiveData<RepoSearchResult> = Transformations.map(_keyword) { keyword ->
        repository.searchRepo(keyword)
    }

    val repoFound: LiveData<PagedList<Repo>> = Transformations.switchMap(searchResult) { it.data }

    val networkError: LiveData<String> = Transformations.switchMap(searchResult) { it.networkError }

    val searchHistory = MediatorLiveData<String>()

    init {
        searchHistory.value = "Search History: \n\n"
        searchHistory.addSource(_keyword) { keyword ->
            searchHistory.value += "Keyword: $keyword\n"
        }

        searchHistory.addSource(repoFound) { repoFound ->
            searchHistory.value += "Result: ${repoFound.size}\n"
        }
    }
}
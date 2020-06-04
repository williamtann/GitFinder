package com.example.gitfinder

import androidx.lifecycle.*

class MainViewModel: ViewModel() {

    private var repository: MainRepository = MainRepository()

    private val _keyword = MutableLiveData<String>()
    val keyword: LiveData<String>
        get() = _keyword

    val searchEnabled: LiveData<Boolean> = Transformations.map(_keyword) { keyword -> !keyword.isNullOrEmpty() }
    val searchResult: LiveData<String> = Transformations.switchMap(_keyword) { repository.searchRepo(it) }
    val searchHistory = MediatorLiveData<String>()

    init {
        searchHistory.value = "Search History:\n\n"
        searchHistory.addSource(_keyword) {
            searchHistory.value += "Keyword: $it\n"
        }
        searchHistory.addSource(searchResult) {
            searchHistory.value += "Result: $it\n"
        }
    }

    fun search(keyword: String) {
        _keyword.value = keyword
    }
}
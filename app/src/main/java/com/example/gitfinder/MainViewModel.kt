package com.example.gitfinder

import androidx.lifecycle.*

class MainViewModel: ViewModel() {

    private val repository = MainRepository()

    private val _keyword: MutableLiveData<String> = MutableLiveData()
    val keyword: LiveData<String>
        get() = _keyword

    fun updateKeyword(input: String) {
        _keyword.value = input
    }

    val searchEnabled: LiveData<Boolean> = Transformations.map(_keyword) { keyword ->
        !keyword.isNullOrEmpty()
    }

    val searchResult: LiveData<String> = Transformations.switchMap(_keyword) { keyword ->
        repository.searchRepo(keyword)
    }

    val searchHistory = MediatorLiveData<String>()

    init {
        searchHistory.value = "Search History: \n\n"
        searchHistory.addSource(_keyword) { keyword ->
            searchHistory.value += "Keyword: $keyword\n"
        }

        searchHistory.addSource(searchResult) { searchResult ->
            searchHistory.value += "Result: $searchResult\n"
        }
    }
}
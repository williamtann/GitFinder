package com.example.gitfinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

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
}
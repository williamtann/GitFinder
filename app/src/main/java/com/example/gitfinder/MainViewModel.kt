package com.example.gitfinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _keyword: MutableLiveData<String> = MutableLiveData()
    val keyword: LiveData<String>
        get() = _keyword

    fun updateKeyword(input: String) {
        _keyword.value = input
    }
}
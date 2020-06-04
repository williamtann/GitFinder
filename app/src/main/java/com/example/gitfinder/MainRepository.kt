package com.example.gitfinder

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainRepository {

    fun searchRepo(query: String): LiveData<String> {
        val repoFound = MutableLiveData<String>()
        if (query.isNotEmpty()) {
            // Do a network request that for example takes 3 seconds to return a value
            val handler = Handler()
            handler.postDelayed({
                repoFound.postValue("${query}123")
            }, 3000)
        }
        return repoFound
    }
}
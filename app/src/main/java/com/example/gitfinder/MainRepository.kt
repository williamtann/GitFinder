package com.example.gitfinder

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainRepository {

    // do a network request that for example takes 3 seconds to return a value
    fun searchRepo(keyword: String): LiveData<String> {
        val repoFound = MutableLiveData<String>()
        if (keyword.isNotEmpty()) {
            val handler = Handler()
            handler.postDelayed({
                repoFound.postValue(keyword + "123")
            }, 3000)
        }
        return repoFound
    }
}
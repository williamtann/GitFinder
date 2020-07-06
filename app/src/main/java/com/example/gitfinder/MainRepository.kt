package com.example.gitfinder

import androidx.lifecycle.MutableLiveData
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.datamodel.RepoSearchResponse
import com.example.gitfinder.viewmodel.RepoSearchResult
import com.example.gitfinder.service.RemoteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val remoteService: RemoteService) {

    private val networkError = MutableLiveData<String>()

    fun searchRepo(query: String): RepoSearchResult {
        val repoFound = MutableLiveData<List<Repo>>()

        remoteService.searchRepo(query).enqueue(object: Callback<RepoSearchResponse> {
            override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                networkError.postValue(t.message ?: "unknown error")
            }

            override fun onResponse(
                call: Call<RepoSearchResponse>,
                response: Response<RepoSearchResponse>
            ) {
                if (response.isSuccessful) {
                    repoFound.postValue(response.body()?.items ?: emptyList())
                } else {
                    networkError.postValue(response.errorBody()?.string())
                }
            }

        })
        return RepoSearchResult(repoFound, networkError)
    }
}
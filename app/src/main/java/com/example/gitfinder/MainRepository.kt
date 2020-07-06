package com.example.gitfinder

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.datamodel.RepoSearchResponse
import com.example.gitfinder.model.RepoSearchResult
import com.example.gitfinder.service.RemoteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val service: RemoteService) {

    private var isRequestInProgress = false
    private val networkError = MutableLiveData<String>()

    @Deprecated("Mock function")
    fun searchRepoMock(query: String): LiveData<String> {
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

    fun searchRepo(query: String): RepoSearchResult {
        val reposLiveData = MutableLiveData<List<Repo>>()
        if (!isRequestInProgress) {
            isRequestInProgress = true
            service.searchRepos(query).enqueue(object : Callback<RepoSearchResponse> {
                    override fun onFailure(call: Call<RepoSearchResponse>?, t: Throwable) {
                        networkError.postValue(t.message ?: "unknown error")
                        isRequestInProgress = false
                    }

                    override fun onResponse(call: Call<RepoSearchResponse>?, response: Response<RepoSearchResponse>) {
                        if (response.isSuccessful) {
                            val repos = response.body()?.items ?: emptyList()
                            reposLiveData.postValue(repos)
                        } else {
                            networkError.postValue(response.errorBody()?.string() ?: "unknown error")
                        }
                        isRequestInProgress = false
                    }
                }
            )
        }
        return RepoSearchResult(reposLiveData, networkError)
    }
}
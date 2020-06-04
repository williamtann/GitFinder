package com.example.gitfinder.service

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.datamodel.RepoSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RepoDataSource(
    private val remoteService: RemoteService,
    private val query: String
) : PageKeyedDataSource<Int, Repo>() {

    val networkError = MutableLiveData<String>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repo>) {
        try {
            val response = remoteService.searchRepos(query, 1, params.requestedLoadSize).execute()
            if (response.isSuccessful && response.body() != null) {
                callback.onResult(response.body()!!.items, null, 2)
            } else {
                networkError.postValue(response.errorBody()?.string() ?: "unknown error")
            }
        } catch (ioException: IOException) {
            networkError.postValue("unknown error")
        }
//        remoteService.searchRepos(query, 1, params.requestedLoadSize).enqueue(object : Callback<RepoSearchResponse> {
//            override fun onFailure(call: Call<RepoSearchResponse>?, t: Throwable) {
//                networkError.postValue(t.message ?: "unknown error")
//            }
//
//            override fun onResponse(call: Call<RepoSearchResponse>?, response: Response<RepoSearchResponse>) {
//                if (response.isSuccessful) {
//                    val repos = response.body()?.items ?: emptyList()
//                    callback.onResult(repos, null, 2)
//                } else {
//                    networkError.postValue(response.errorBody()?.string() ?: "unknown error")
//                }
//            }
//        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        remoteService.searchRepos(query, params.key, params.requestedLoadSize).enqueue(object : Callback<RepoSearchResponse> {
            override fun onFailure(call: Call<RepoSearchResponse>?, t: Throwable) {
                networkError.postValue(t.message ?: "unknown error")
            }

            override fun onResponse(call: Call<RepoSearchResponse>?, response: Response<RepoSearchResponse>) {
                if (response.isSuccessful) {
                    val repos = response.body()?.items ?: emptyList()
                    callback.onResult(repos, params.key + 1)
                } else {
                    networkError.postValue(response.errorBody()?.string() ?: "unknown error")
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {}
}
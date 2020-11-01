package com.example.gitfinder

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.datamodel.RepoSearchResponse
import com.example.gitfinder.service.RemoteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RepoDataSource(private val remoteService: RemoteService,
                     private val query: String
): PageKeyedDataSource<Int, Repo>() {

    val networkError = MutableLiveData<String>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {
        try {
            val response = remoteService.searchRepo(query, 1, params.requestedLoadSize).execute()
            if (response.isSuccessful && response.body() != null) {
                callback.onResult(response.body()!!.items, null, 2)
            } else {
                networkError.postValue(response.errorBody()?.string() ?: "unknown error")
            }
        } catch (exception: IOException) {
            networkError.postValue(exception.message ?: "unknown error")
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        remoteService.searchRepo(query, params.key, params.requestedLoadSize).enqueue(object: Callback<RepoSearchResponse> {

            override fun onResponse(
                call: Call<RepoSearchResponse>,
                response: Response<RepoSearchResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    callback.onResult(response.body()!!.items, params.key + 1)
                } else {
                    networkError.postValue(response.errorBody()?.string() ?: "unknown error")
                }
            }

            override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                networkError.postValue(t.message ?: "unknown error")
            }

        })
    }
}
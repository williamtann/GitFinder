package com.example.gitfinder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.service.RemoteService
import com.example.gitfinder.viewmodel.RepoDetailResult
import com.example.gitfinder.viewmodel.RepoSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val remoteService: RemoteService) {

    fun searchRepo(query: String): RepoSearchResult {
        val pagedListConfig = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()
        val dataFactory = RepoDataFactory(remoteService, query)
        val pagedData = LivePagedListBuilder(dataFactory, pagedListConfig).build()
        val networkError = Transformations.switchMap(dataFactory.dataSource) { dataSource ->
            dataSource.networkError
        }

        return RepoSearchResult(pagedData, networkError)
    }

    fun getRepoById(id: Long): RepoDetailResult {
        val result = MutableLiveData<Repo>()
        val networkError = MutableLiveData<String>()

        remoteService.getRepoById(id).enqueue(object : Callback<Repo> {
            override fun onFailure(call: Call<Repo>, t: Throwable) {
                networkError.postValue(t.message ?: "unknown error")
            }

            override fun onResponse(call: Call<Repo>, response: Response<Repo>) {
                if (response.isSuccessful && response.body() != null) {
                    result.postValue(response.body())
                } else {
                    networkError.postValue(response.errorBody()?.string() ?: "unknown error")
                }
            }
        })

        return RepoDetailResult(result, networkError)
    }
}
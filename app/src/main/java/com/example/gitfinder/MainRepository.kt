package com.example.gitfinder

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.datamodel.RepoSearchResponse
import com.example.gitfinder.model.RepoSearchResult
import com.example.gitfinder.service.RemoteService
import com.example.gitfinder.service.RepoDataFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val service: RemoteService) {

    fun searchPagedRepo(query: String): RepoSearchResult {
        val pagedListConfig = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()
        val dataFactory = RepoDataFactory(service, query)
        val pagedData = LivePagedListBuilder(dataFactory, pagedListConfig).build()
        val networkError = Transformations.switchMap(dataFactory.liveDataSource) { it.networkError }

        return RepoSearchResult(pagedData, networkError)
    }

//    private val networkError = MutableLiveData<String>()
//
//    @Deprecated("Mock function")
//    fun searchRepoMock(query: String): LiveData<String> {
//        val repoFound = MutableLiveData<String>()
//        if (query.isNotEmpty()) {
//            // Do a network request that for example takes 3 seconds to return a value
//            val handler = Handler()
//            handler.postDelayed({
//                repoFound.postValue("${query}123")
//            }, 3000)
//        }
//        return repoFound
//    }
//
//    fun searchRepo(query: String): RepoSearchResult {
//        val reposLiveData = MutableLiveData<PagedList<Repo>>()
//
//        service.searchRepos(query).enqueue(object : Callback<RepoSearchResponse> {
//                override fun onFailure(call: Call<RepoSearchResponse>?, t: Throwable) {
//                    networkError.postValue(t.message ?: "unknown error")
//                }
//
//                override fun onResponse(call: Call<RepoSearchResponse>?, response: Response<RepoSearchResponse>) {
//                    if (response.isSuccessful) {
//                        val repos = response.body()?.items ?: emptyList()
//                        reposLiveData.postValue(repos)
//                    } else {
//                        networkError.postValue(response.errorBody()?.string() ?: "unknown error")
//                    }
//                }
//            }
//        )
//        return RepoSearchResult(reposLiveData, networkError)
//    }
}
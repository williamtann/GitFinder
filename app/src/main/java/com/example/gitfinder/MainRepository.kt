package com.example.gitfinder

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gitfinder.database.EntityBridge
import com.example.gitfinder.database.RepoDatabase
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.service.RemoteService
import com.example.gitfinder.viewmodel.RepoDetailResult
import com.example.gitfinder.viewmodel.RepoSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val remoteService: RemoteService, private val application: Application) {

    private val database by lazy { RepoDatabase.getDatabase(application) }

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

    suspend fun saveRepo(repo: Repo) {
        database.repoDao().insertRepo(EntityBridge.repoDataModelToEntity(repo))
    }

    fun getSavedRepo(): LiveData<List<Repo>> = Transformations.map(database.repoDao().getRepoList()) { entityList ->
        val repoList = mutableListOf<Repo>()
        for (entity in entityList) {
            repoList.add(EntityBridge.repoEntityToDataModel(entity))
        }
        repoList
    }

    fun getPagedSavedRepo(): LiveData<PagedList<Repo>> {
        val pagedListConfig = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()
        val dataFactory = database.repoDao().getRepoPagedList().map { repoEntity ->
            EntityBridge.repoEntityToDataModel(repoEntity)
        }

        return LivePagedListBuilder<Int, Repo>(dataFactory, pagedListConfig).build()
    }
}
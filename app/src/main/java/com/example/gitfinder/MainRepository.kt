package com.example.gitfinder

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gitfinder.service.RemoteService
import com.example.gitfinder.viewmodel.RepoSearchResult

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
}
package com.example.gitfinder

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.service.RemoteService

class RepoDataFactory(private val remoteService: RemoteService,
                      private val query: String
): DataSource.Factory<Int, Repo>() {

    val dataSource = MutableLiveData<RepoDataSource>()

    override fun create(): DataSource<Int, Repo> {
        val repoDataSource = RepoDataSource(remoteService, query)
        dataSource.postValue(repoDataSource)
        return repoDataSource
    }
}
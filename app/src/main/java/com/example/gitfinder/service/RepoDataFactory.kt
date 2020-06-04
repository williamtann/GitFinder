package com.example.gitfinder.service

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.gitfinder.datamodel.Repo

class RepoDataFactory(
    private val remoteService: RemoteService,
    private val query: String
) : DataSource.Factory<Int, Repo>() {

    var liveDataSource: MutableLiveData<RepoDataSource> = MutableLiveData<RepoDataSource>()

    override fun create(): DataSource<Int, Repo> {
        val dataSource = RepoDataSource(remoteService, query)
        liveDataSource.postValue(dataSource)
        return dataSource
    }
}
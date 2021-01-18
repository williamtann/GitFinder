package com.example.gitfinder.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.gitfinder.MainRepository
import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.service.RemoteService

class BookmarkViewModel(application: Application): AndroidViewModel(application) {

    private val repository = MainRepository(RemoteService.create(), application)

//    Example of getting cache data as live data
//    val savedRepo: LiveData<List<Repo>> = repository.getSavedRepo()

    val pagedSavedRepo: LiveData<PagedList<Repo>> = repository.getPagedSavedRepo()
}
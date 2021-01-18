package com.example.gitfinder.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.gitfinder.database.entity.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: RepoEntity)

    @Query("SELECT * FROM table_repo ORDER BY name ASC")
    fun getRepoList(): LiveData<List<RepoEntity>>

    @Query("SELECT * FROM table_repo ORDER BY name ASC")
    fun getRepoPagedList(): DataSource.Factory<Int, RepoEntity>
}
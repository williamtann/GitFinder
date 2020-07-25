package com.example.gitfinder.service

import com.example.gitfinder.datamodel.Repo
import com.example.gitfinder.datamodel.RepoSearchResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    /**
     * Search repositories with keyword
     */
    @GET("/search/repositories")
    fun searchRepo(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Call<RepoSearchResponse>

    /**
     * Get repository detail by id
     */
    @GET("/repositories/{id}")
    fun getRepoById(
        @Path("id") repoId: Long
    ): Call<Repo>

    companion object {
        private const val BASE_URL = "https://api.github.com"

        fun create(): RemoteService {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RemoteService::class.java)
        }
    }
}
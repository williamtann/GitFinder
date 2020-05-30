package com.example.gitfinder.datamodel

import com.google.gson.annotations.SerializedName

data class Repo(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("full_name") val fullName: String,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("html_url") val url: String,
    @field:SerializedName("stargazers_count") val stargazers: Int,
    @field:SerializedName("watchers_count") val watchers: Int
)

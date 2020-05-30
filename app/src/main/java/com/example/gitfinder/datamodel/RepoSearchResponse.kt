package com.example.gitfinder.datamodel

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("items") val items: List<Repo> = emptyList()
)

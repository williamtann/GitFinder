package com.example.gitfinder.datamodel

import com.google.gson.annotations.SerializedName

class RepoSearchResponse (
    @field:SerializedName("items") val items: List<Repo>
)
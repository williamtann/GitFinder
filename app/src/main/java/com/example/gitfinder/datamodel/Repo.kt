package com.example.gitfinder.datamodel

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo (
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("full_name") val fullName: String,
    @field:SerializedName("html_url") val url: String,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("stargazers_count") val stargazers: Int,
    @field:SerializedName("watchers_count") val watchers: Int
) : Parcelable
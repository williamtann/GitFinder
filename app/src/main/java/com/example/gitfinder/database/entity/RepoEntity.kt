package com.example.gitfinder.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_repo")
data class RepoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "html_url") val url: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "stargazers_count") val stargazers: Int,
    @ColumnInfo(name = "watchers_count") val watchers: Int,
    @ColumnInfo(name = "note") val note: String?
)
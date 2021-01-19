package com.example.gitfinder.database

import com.example.gitfinder.database.entity.RepoEntity
import com.example.gitfinder.datamodel.Repo

object EntityBridge {

    fun repoDataModelToEntity(dataModel: Repo): RepoEntity =
        RepoEntity(
            dataModel.id,
            dataModel.name,
            dataModel.fullName,
            dataModel.url,
            dataModel.description,
            dataModel.stargazers,
            dataModel.watchers,
            dataModel.note
        )

    fun repoEntityToDataModel(entity: RepoEntity): Repo =
        Repo(
            entity.id,
            entity.name,
            entity.fullName,
            entity.url,
            entity.description,
            entity.stargazers,
            entity.watchers,
            entity.note
        )
}
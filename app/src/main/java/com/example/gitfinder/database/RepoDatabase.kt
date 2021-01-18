package com.example.gitfinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gitfinder.database.dao.RepoDao
import com.example.gitfinder.database.entity.RepoEntity

@Database(entities = [RepoEntity::class], version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {

        @Volatile
        private var INSTANCE: RepoDatabase? = null

        fun getDatabase(context: Context): RepoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RepoDatabase::class.java,
                    "database_repo"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.dzikril.submission1.di

import android.content.Context
import com.dzikril.submission1.data.local.room.StoryDb
import com.dzikril.submission1.data.repo.StoryRepos
import com.dzikril.submission1.data.remote.retrofit.ApiConfig

object StoryInjection {
    fun provideRepository(context: Context): StoryRepos {
        val apiService = ApiConfig.getApiService()
        val database = StoryDb.getDatabase(context)
        return StoryRepos(apiService, database)
    }
}
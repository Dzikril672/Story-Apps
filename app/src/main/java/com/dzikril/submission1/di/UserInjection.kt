package com.dzikril.submission1.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dzikril.submission1.data.repo.UserRepos
import com.dzikril.submission1.data.remote.retrofit.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object UserInjection {
    fun provideRepository(context: Context): UserRepos {
        val apiService = ApiConfig.getApiService()
        return UserRepos.getInstance(context.dataStore, apiService)
    }
}
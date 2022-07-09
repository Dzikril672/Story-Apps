package com.dzikril.submission1.ui.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzikril.submission1.data.repo.StoryRepos
import com.dzikril.submission1.data.repo.UserRepos
import com.dzikril.submission1.di.StoryInjection
import com.dzikril.submission1.di.UserInjection
import com.dzikril.submission1.ui.main.MainViewModel
import com.dzikril.submission1.ui.maps.MapsViewModel
import com.dzikril.submission1.ui.story.StoryViewModel

class StoryViewModelFactory private constructor(private val userRepo: UserRepos, private val storyRepo: StoryRepos) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepo, storyRepo) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(storyRepo) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(storyRepo) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null
        fun getInstance(context: Context): StoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(UserInjection.provideRepository(context), StoryInjection.provideRepository(context))
            }.also { instance = it }
    }
}
package com.dzikril.submission1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dzikril.submission1.data.local.entity.Story
import com.dzikril.submission1.data.repo.StoryRepos
import com.dzikril.submission1.data.repo.UserRepos
import kotlinx.coroutines.launch

class MainViewModel(private val userRepo: UserRepos, private val storyRepo: StoryRepos) : ViewModel() {

    fun getToken() : LiveData<String> {
        return userRepo.getToken().asLiveData()
    }

    fun isLogin() : LiveData<Boolean> {
        return userRepo.isLogin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }

    fun getStories(token: String) : LiveData<PagingData<Story>> =
        storyRepo.getStories(token).cachedIn(viewModelScope)
}
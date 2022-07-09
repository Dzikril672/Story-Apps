package com.dzikril.submission1.ui.maps

import androidx.lifecycle.ViewModel
import com.dzikril.submission1.data.repo.StoryRepos

class MapsViewModel(private val storyRepo: StoryRepos) : ViewModel() {
    fun getStories(token: String) = storyRepo.getStoryLocation(token)
}
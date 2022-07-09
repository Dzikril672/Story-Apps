package com.dzikril.submission1.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.dzikril.submission1.data.StoryRemoteMedia
import com.dzikril.submission1.data.local.entity.Story
import com.dzikril.submission1.data.local.room.StoryDb
import com.dzikril.submission1.data.result.Result
import com.dzikril.submission1.data.remote.response.StoryResponse
import com.dzikril.submission1.data.remote.response.StoryUploadResponse
import com.dzikril.submission1.data.remote.retrofit.ApiService
import com.dzikril.submission1.utils.wrapEspressoIdlingResource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class StoryRepos (private val apiService: ApiService, private val storyDatabase: StoryDb){

    fun uploadStory(token: String, imageMultipart: MultipartBody.Part, desc: RequestBody, lat: RequestBody?, lon: RequestBody?): LiveData<Result<StoryUploadResponse>> = liveData{
        emit(Result.Loading)
        try {
            val client = apiService.uploadStory("Bearer $token",imageMultipart, desc, lat, lon)
            emit(Result.Success(client))
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStoryLocation(token: String) : LiveData<Result<StoryResponse>> = liveData{
        emit(Result.Loading)
        try {
            val client = apiService.getStories("Bearer $token", location = 1)
            emit(Result.Success(client))
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStories(token: String): LiveData<PagingData<Story>> {
        wrapEspressoIdlingResource {
            @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                remoteMediator = StoryRemoteMedia(storyDatabase, apiService, token),
                pagingSourceFactory = {
                    storyDatabase.storyDao().getAllStories()
                }
            ).liveData
        }
    }
}
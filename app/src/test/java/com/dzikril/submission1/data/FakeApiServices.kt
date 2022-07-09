package com.dzikril.submission1.data

import com.dzikril.submission1.data.remote.response.LoginResponse
import com.dzikril.submission1.data.remote.response.RegisterResponse
import com.dzikril.submission1.data.remote.response.StoryResponse
import com.dzikril.submission1.data.remote.response.StoryUploadResponse
import com.dzikril.submission1.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiServices : ApiService {
    private val dummyLoginResponse = DummyData.generateDummyLoginResponse()
    private val dummySignupResponse = DummyData.generateDummyRegisterResponse()
    private val dummyStories = DummyData.generateDummyStoryResponse()
    private val dummyUploadStory = DummyData.generateDummyStoryUploadResponse()

    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return dummySignupResponse
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        return dummyLoginResponse
    }

    override suspend fun getStories(
        token: String,
        page: Int?,
        size: Int?,
        location: Int
    ): StoryResponse {
        return dummyStories
    }

    override suspend fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): StoryUploadResponse {
        return dummyUploadStory
    }
}
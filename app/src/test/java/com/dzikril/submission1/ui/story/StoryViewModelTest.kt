package com.dzikril.submission1.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dzikril.submission1.data.DummyData
import com.dzikril.submission1.data.remote.response.StoryUploadResponse
import com.dzikril.submission1.data.repo.StoryRepos
import com.dzikril.submission1.data.result.Result
import com.dzikril.submission1.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepos
    private lateinit var storyViewModel: StoryViewModel
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyUploadResponse = DummyData.generateDummyStoryUploadResponse()
    private val dummyFile = DummyData.generateDummyMultipartFile()
    private val dummyRequestBody = DummyData.generateDummyRequestBody()

    @Before
    fun setup() {
        storyViewModel = StoryViewModel(storyRepository)
    }

    @Test
    fun `Upload story failed`() {
        val expectedUploadResponse = MutableLiveData<Result<StoryUploadResponse>>()
        expectedUploadResponse.value = Result.Error("Error")

        Mockito.`when`(
            storyViewModel.uploadStory(
                dummyToken,
                dummyFile,
                dummyRequestBody,
                null,
                null
            )
        ).thenReturn(expectedUploadResponse)

        val actualUploadResponse = storyViewModel.uploadStory(dummyToken,dummyFile, dummyRequestBody, null, null).getOrAwaitValue()
        Mockito.verify(storyRepository).uploadStory(dummyToken, dummyFile, dummyRequestBody, null, null)
        Assert.assertNotNull(actualUploadResponse)
        Assert.assertTrue(actualUploadResponse is Result.Error)
    }

    @Test
    fun `Upload story successfully`() {
        val expectedUploadResponse = MutableLiveData<Result<StoryUploadResponse>>()
        expectedUploadResponse.value = Result.Success(dummyUploadResponse)

        Mockito.`when`(
            storyViewModel.uploadStory(
                dummyToken,
                dummyFile,
                dummyRequestBody,
                null,
                null
            )
        ).thenReturn(expectedUploadResponse)

        val actualUploadResponse = storyViewModel.uploadStory(dummyToken,dummyFile, dummyRequestBody, null, null).getOrAwaitValue()
        Mockito.verify(storyRepository).uploadStory(dummyToken, dummyFile, dummyRequestBody, null, null)
        Assert.assertNotNull(actualUploadResponse)
        Assert.assertTrue(actualUploadResponse is Result.Success)
    }

}
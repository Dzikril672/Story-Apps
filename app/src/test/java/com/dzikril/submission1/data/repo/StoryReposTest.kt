package com.dzikril.submission1.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.dzikril.submission1.CoroutineRules
import com.dzikril.submission1.PaggingDataSourceTest
import com.dzikril.submission1.adapter.ListStoryAdapter
import com.dzikril.submission1.data.DummyData
import com.dzikril.submission1.data.FakeApiServices
import com.dzikril.submission1.data.local.entity.Story
import com.dzikril.submission1.data.local.room.StoryDb
import com.dzikril.submission1.data.remote.retrofit.ApiService
import com.dzikril.submission1.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryReposTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Mock
    private lateinit var storyDatabase: StoryDb
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var mockStoryRepository: StoryRepos
    private lateinit var storyRepository: StoryRepos
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyFile = DummyData.generateDummyMultipartFile()
    private val dummyRequestBody = DummyData.generateDummyRequestBody()

    @Before
    fun setup() {
        apiService = FakeApiServices()
        storyRepository = StoryRepos(apiService, storyDatabase)
    }

    @Test
    fun `Upload story successfully`() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DummyData.generateDummyStoryUploadResponse()
        val actualResponse = apiService.uploadStory(dummyToken,dummyFile, dummyRequestBody, null, null)
        Assert.assertNotNull(expectedResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `when stories location Should Not Null`() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DummyData.generateDummyStoryResponse()
        val actualResponse = apiService.getStories(dummyToken, location = 1)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse.listStory.size, actualResponse.listStory.size)
    }

    @Test
    fun `Get stories paging - successfully`() = mainCoroutineRule.runBlockingTest {
        val dummyStories = DummyData.generateDummyStoryList()

        val expectedStories = MutableLiveData<PagingData<Story>>()
        expectedStories.value = PaggingDataSourceTest.snapshot(dummyStories)

        Mockito.`when`(mockStoryRepository.getStories(dummyToken)).thenReturn(expectedStories)

        val noopListUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }
        val actualStories = mockStoryRepository.getStories(dummyToken).getOrAwaitValue()
        val storyDiffer = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher
        )
        storyDiffer.submitData(actualStories)
        Assert.assertNotNull(storyDiffer.snapshot())
        Assert.assertEquals(dummyStories.size, storyDiffer.snapshot().size)
    }
}
package com.dzikril.submission1.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dzikril.submission1.CoroutineRules
import com.dzikril.submission1.data.DummyData
import com.dzikril.submission1.data.FakeApiServices
import com.dzikril.submission1.data.remote.retrofit.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserReposTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var dataStore : DataStore<Preferences>
    private lateinit var userRepository: UserRepos

    private val dummyName = "User"
    private val dummyEmail = "user@email.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        apiService = FakeApiServices()
        userRepository = UserRepos.getInstance(dataStore, apiService)
    }

    @Test
    fun `when register response Should Not Null`() = mainCoroutineRule.runBlockingTest{
        val expectedResponse = DummyData.generateDummyRegisterResponse()
        val actualResponse = apiService.register(dummyName, dummyEmail, dummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(actualResponse, expectedResponse)
    }

    @Test
    fun `when login response Should Not Null`() = mainCoroutineRule.runBlockingTest{
        val expectedResponse = DummyData.generateDummyLoginResponse()
        val actualResponse = apiService.login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(actualResponse, expectedResponse)
    }

}
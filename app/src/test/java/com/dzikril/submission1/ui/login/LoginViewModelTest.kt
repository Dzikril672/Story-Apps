package com.dzikril.submission1.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dzikril.submission1.CoroutineRules
import com.dzikril.submission1.data.DummyData
import com.dzikril.submission1.data.result.Result
import com.dzikril.submission1.data.remote.response.LoginResponse
import com.dzikril.submission1.data.repo.UserRepos
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
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepos
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLoginResponse = DummyData.generateDummyLoginResponse()
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyEmail = "user@email.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(userRepository)
    }

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Test
    fun `when signup failed and Result Error`() {
        val loginResponse = MutableLiveData<Result<LoginResponse>>()
        loginResponse.value = Result.Error("Error")

        Mockito.`when`(loginViewModel.login(dummyEmail, dummyPassword)).thenReturn(loginResponse)

        val actualLoginResponse = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualLoginResponse)
        Assert.assertTrue(actualLoginResponse is Result.Error)
    }

    @Test
    fun `when login success and Result Success`() {
        val expectedLoginResponse = MutableLiveData<Result<LoginResponse>>()
        expectedLoginResponse.value = Result.Success(dummyLoginResponse)

        Mockito.`when`(loginViewModel.login(dummyEmail, dummyPassword)).thenReturn(expectedLoginResponse)

        val actualLoginResponse = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualLoginResponse)
        Assert.assertTrue(actualLoginResponse is Result.Success)
        Assert.assertSame(dummyLoginResponse, (actualLoginResponse as Result.Success).data)
    }

    @Test
    fun `Save token successfully`() = mainCoroutineRule.runBlockingTest {
        loginViewModel.setToken(dummyToken, true)
        Mockito.verify(userRepository).setToken(dummyToken, true)
    }
}
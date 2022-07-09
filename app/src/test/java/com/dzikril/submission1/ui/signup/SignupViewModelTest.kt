package com.dzikril.submission1.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dzikril.submission1.data.DummyData
import com.dzikril.submission1.data.remote.response.RegisterResponse
import com.dzikril.submission1.data.repo.UserRepos
import com.dzikril.submission1.getOrAwaitValue
import com.dzikril.submission1.data.result.Result
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignupViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepos
    private lateinit var signupViewModel: SignupViewModel
    private val dummySignupResponse = DummyData.generateDummyRegisterResponse()
    private val dummyName = "User"
    private val dummyEmail = "user@email.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        signupViewModel = SignupViewModel(userRepository)
    }

    @Test
    fun `when signup failed and Result Error`() {
        val signupResponse = MutableLiveData<Result<RegisterResponse>>()
        signupResponse.value = Result.Error("Error")

        Mockito.`when`(signupViewModel.register(dummyName, dummyEmail, dummyPassword)).thenReturn(signupResponse)

        val actualSignupResponse = signupViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).register(dummyName, dummyEmail, dummyPassword)
        Assert.assertNotNull(actualSignupResponse)
        Assert.assertTrue(actualSignupResponse is Result.Error)
    }

    @Test
    fun `when signup success and Result Success`() {
        val expectedSignupResponse = MutableLiveData<Result<RegisterResponse>>()
        expectedSignupResponse.value = Result.Success(dummySignupResponse)

        Mockito.`when`(signupViewModel.register(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedSignupResponse)

        val actualSignupResponse = signupViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).register(dummyName, dummyEmail, dummyPassword)
        Assert.assertNotNull(actualSignupResponse)
        Assert.assertTrue(actualSignupResponse is Result.Success)
        Assert.assertSame(dummySignupResponse, (actualSignupResponse as Result.Success).data)
    }
}
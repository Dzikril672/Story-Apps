package com.dzikril.submission1.ui.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzikril.submission1.data.repo.UserRepos
import com.dzikril.submission1.di.UserInjection
import com.dzikril.submission1.ui.login.LoginViewModel
import com.dzikril.submission1.ui.signup.SignupViewModel

class UserViewModelFactory (private val userRepo: UserRepos) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(userRepo) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: UserViewModelFactory? = null
        fun getInstance(context: Context): UserViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: UserViewModelFactory(UserInjection.provideRepository(context))
            }.also { instance = it }
    }
}
package com.dzikril.submission1.ui.signup

import androidx.lifecycle.ViewModel
import com.dzikril.submission1.data.repo.UserRepos

class SignupViewModel(private val repo: UserRepos) : ViewModel() {

    fun register(name: String, email: String, password: String) = repo.register(name, email, password)
}
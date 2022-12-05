package com.psi.caffeine.ui.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.psi.caffeine.data.CaffeineDatabase

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {
    
    private val dao = CaffeineDatabase.getInstance(application).caffeineDao()
    
    fun login(username: String, password: String) = dao.getUser(username, password)?.asLiveData()
}
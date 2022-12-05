package com.psi.caffeine.ui.auth.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.psi.caffeine.data.CaffeineDatabase
import com.psi.caffeine.model.User
import kotlinx.coroutines.launch

class RegisterViewModel(
    application: Application
) : AndroidViewModel(application) {
    
    private val dao = CaffeineDatabase.getInstance(application).caffeineDao()
    
    fun isEmailExist(email: String) = dao.isEmailExist(email).asLiveData()
    fun register(user: User) = viewModelScope.launch {
        dao.insertUser(user)
    }
    
}
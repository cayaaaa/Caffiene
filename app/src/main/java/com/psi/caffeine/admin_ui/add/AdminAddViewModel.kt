package com.psi.caffeine.admin_ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.psi.caffeine.data.CaffeineDatabase
import com.psi.caffeine.model.Cafe
import kotlinx.coroutines.launch

class AdminAddViewModel(
    application: Application
): AndroidViewModel(application) {
    
    private val dao = CaffeineDatabase.getInstance(application).caffeineDao()
    
    fun insertCafe(cafe: Cafe) {
        viewModelScope.launch {
            dao.insertCafe(cafe)
        }
    }
    
}
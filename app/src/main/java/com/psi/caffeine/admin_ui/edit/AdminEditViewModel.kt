package com.psi.caffeine.admin_ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.psi.caffeine.data.CaffeineDatabase
import com.psi.caffeine.model.Cafe
import kotlinx.coroutines.launch

class AdminEditViewModel(
    application: Application
): AndroidViewModel(application) {
    
    private val dao = CaffeineDatabase.getInstance(application).caffeineDao()
    
    private val _cafe = MutableLiveData<Cafe>()
    val cafe: LiveData<Cafe> = _cafe
    
    fun getCafe(cafeId: Int) {
        viewModelScope.launch {
            dao.getCafeDetail(cafeId).collect {
                _cafe.value = it
            }
        }
    }
    
    fun updateCafe(cafe: Cafe) {
        viewModelScope.launch {
            dao.updateCafe(cafe)
        }
    }
    
}
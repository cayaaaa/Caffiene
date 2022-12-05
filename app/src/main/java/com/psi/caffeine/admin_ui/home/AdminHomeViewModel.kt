package com.psi.caffeine.admin_ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.psi.caffeine.data.CaffeineDatabase
import com.psi.caffeine.model.Cafe
import kotlinx.coroutines.launch

class AdminHomeViewModel(
    application: Application
): AndroidViewModel(application) {
    
    private val dao = CaffeineDatabase.getInstance(application).caffeineDao()
    
    private val _cafeList = MutableLiveData<List<Cafe>>()
    val cafeList: LiveData<List<Cafe>> = _cafeList
    
    init {
        viewModelScope.launch {
            dao.getAllCafes().collect {
                _cafeList.value = it
            }
        }
    }
    
    fun deleteCafe(cafeId: Int) {
        viewModelScope.launch {
            dao.deleteCafe(cafeId)
        }
    }
}

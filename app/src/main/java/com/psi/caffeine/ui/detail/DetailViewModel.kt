package com.psi.caffeine.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psi.caffeine.data.CaffeineDatabase
import com.psi.caffeine.data.Dummy
import com.psi.caffeine.model.Cafe
import com.psi.caffeine.model.Comment
import com.psi.caffeine.model.Menu
import kotlinx.coroutines.launch

class DetailViewModel(
    application: Application
): AndroidViewModel(application) {
    
    private val dao = CaffeineDatabase.getInstance(application).caffeineDao()
    
    private val _menuList = MutableLiveData<List<Menu>>()
    val menuList: LiveData<List<Menu>> = _menuList
    
    private val _cafeDetail = MutableLiveData<Cafe>()
    val cafeDetail: LiveData<Cafe> = _cafeDetail
    
    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments
    
    fun setDetailCafe(cafeId: Int) {
        viewModelScope.launch {
            dao.getCafeDetail(cafeId).collect {
                _cafeDetail.value = it
            }
        }
    }
    
    init {
        viewModelScope.launch {
            dao.getMenus().collect {
                _menuList.value = it
            }
        }
    
        _comments.value = Dummy.getComments()
    }
}
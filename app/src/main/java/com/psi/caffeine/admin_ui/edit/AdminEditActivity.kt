package com.psi.caffeine.admin_ui.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.psi.caffeine.R
import com.psi.caffeine.databinding.ActivityAdminEditBinding
import com.psi.caffeine.model.Cafe

class AdminEditActivity : AppCompatActivity() {
    
    companion object {
        const val EXTRA_CAFE_ID = "extra_cafe_id"
    }
    
    private lateinit var binding: ActivityAdminEditBinding
    private lateinit var viewModel: AdminEditViewModel
    private lateinit var cafe: Cafe
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[AdminEditViewModel::class.java]
        
        val cafeId = intent.getIntExtra(EXTRA_CAFE_ID, 0)
        
        viewModel.getCafe(cafeId)
        viewModel.cafe.observe(this) {
            cafe = it
            binding.tilName.setText(cafe.cafeName)
            binding.tilDescription.setText(cafe.description)
        }
        
        binding.btnEdit.setOnClickListener {
            cafe.cafeName = binding.tilName.text.toString()
            cafe.description = binding.tilDescription.text.toString()
            viewModel.updateCafe(cafe)
            finish()
        }
    }
}
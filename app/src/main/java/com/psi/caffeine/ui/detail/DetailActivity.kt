package com.psi.caffeine.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.psi.caffeine.adapter.CommentAdapter
import com.psi.caffeine.adapter.MenuAdapter
import com.psi.caffeine.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    
    companion object {
        const val EXTRA_CAFE_ID = "extra_cafe_id"
    }
    
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        
        val menusAdapter = MenuAdapter()
        val commentAdapter = CommentAdapter()
        
        val cafeId = intent.getIntExtra(EXTRA_CAFE_ID, 0)
        
        viewModel.setDetailCafe(cafeId)
        
        viewModel.cafeDetail.observe(this) {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(it.image)
                    .centerCrop()
                    .into(ivHeader)
                tvTitle.text = it.cafeName
                tvDescription.text = it.description
            }
        }
        
        binding.apply {
            rvMenus.apply {
                adapter = menusAdapter
                layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            }
    
            rvComments.apply {
                adapter = commentAdapter
                layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.VERTICAL, false)
            }
        }
        
        viewModel.menuList.observe(this) {
            menusAdapter.submitList(it)
        }
        
        viewModel.comments.observe(this) {
            commentAdapter.submitList(it)
        }
    }
}
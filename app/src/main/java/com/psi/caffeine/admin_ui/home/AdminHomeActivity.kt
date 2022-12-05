package com.psi.caffeine.admin_ui.home

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.psi.caffeine.R
import com.psi.caffeine.adapter.AdminMyCafeAdapter
import com.psi.caffeine.adapter.AdminMyCafeListener
import com.psi.caffeine.admin_ui.add.AdminAddActivity
import com.psi.caffeine.databinding.ActivityAdminHomeBinding

class AdminHomeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAdminHomeBinding
    private lateinit var viewModel: AdminHomeViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val myCafeAdapter = AdminMyCafeAdapter()
        
        viewModel = ViewModelProvider(this)[AdminHomeViewModel::class.java]
        
        binding.apply {
            rvMyCafe.apply {
                adapter = myCafeAdapter
                layoutManager = GridLayoutManager(this@AdminHomeActivity, 2)
            }
        }
        
        viewModel.cafeList.observe(this) {
            myCafeAdapter.submitList(it)
        }
        
        myCafeAdapter.listener = object : AdminMyCafeListener {
            override fun onCafeDeleted(cafeId: Int) {
                AlertDialog.Builder(this@AdminHomeActivity)
                    .setTitle("Hapus cafe")
                    .setMessage("Apakah kamu yakin ingin menghapus cafe?")
                    .setPositiveButton("Ya") { dialogInterface: DialogInterface, i: Int ->
                        viewModel.deleteCafe(cafeId)
                        dialogInterface.dismiss()
                    }
                    .setNegativeButton("Tidak") { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }
                    .show()
            }
    
        }
        
        binding.fabAddCafe.setOnClickListener {
            val intent = Intent(this, AdminAddActivity::class.java)
            startActivity(intent)
        }
        
    }
}
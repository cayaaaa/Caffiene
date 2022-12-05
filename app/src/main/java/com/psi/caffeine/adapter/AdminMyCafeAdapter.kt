package com.psi.caffeine.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.psi.caffeine.admin_ui.edit.AdminEditActivity
import com.psi.caffeine.databinding.ItemListAdminMyCafeBinding
import com.psi.caffeine.model.Cafe

class AdminMyCafeAdapter: RecyclerView.Adapter<AdminMyCafeAdapter.AdminMyCafeViewHolder>() {
    
    private val items = mutableListOf<Cafe>()
    lateinit var listener: AdminMyCafeListener
    
    fun submitList(items: List<Cafe>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    
    inner class AdminMyCafeViewHolder(val view: ItemListAdminMyCafeBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(item: Cafe) {
            Glide.with(view.root)
                .load(item.image)
                .centerCrop()
                .into(view.ivCafe)
            view.tvCafeName.text = item.cafeName
            
            view.ivEdit.setOnClickListener {
                val intent = Intent(itemView.context, AdminEditActivity::class.java)
                intent.putExtra(AdminEditActivity.EXTRA_CAFE_ID, item.cafeId)
                itemView.context.startActivity(intent)
            }
            
            view.ivDelete.setOnClickListener {
                listener.onCafeDeleted(item.cafeId)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminMyCafeViewHolder {
        return AdminMyCafeViewHolder(ItemListAdminMyCafeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: AdminMyCafeViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount(): Int = items.size
}

interface AdminMyCafeListener {
    fun onCafeDeleted(cafeId: Int)
}
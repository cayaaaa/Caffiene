package com.psi.caffeine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.psi.caffeine.databinding.ItemListMenuBinding
import com.psi.caffeine.model.Menu

class MenuAdapter: RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    
    private val menuList = mutableListOf<Menu>()
    
    fun submitList(menuList: List<Menu>) {
        this.menuList.clear()
        this.menuList.addAll(menuList)
        notifyDataSetChanged()
    }
    
    class MenuViewHolder(val view: ItemListMenuBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(menu: Menu) {
            Glide.with(itemView.context)
                .load(menu.image)
                .transform(CenterCrop(), RoundedCorners(8))
                .into(view.ivMenu)
            
            view.tvMenuName.text = menu.name
            view.tvPrice.text = "Rp ${menu.price}"
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = ItemListMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuList[position])
    }
    
    override fun getItemCount(): Int {
        return menuList.size
    }
}
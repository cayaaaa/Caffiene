package com.psi.caffeine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psi.caffeine.databinding.ItemListPromotionBinding

class PromotionAdapter: RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>() {
    
    private val promotions = mutableListOf<Int>()
    
    fun submitList(promotions: List<Int>) {
        this.promotions.clear()
        this.promotions.addAll(promotions)
        notifyDataSetChanged()
    }
    
    class PromotionViewHolder(val view: ItemListPromotionBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(promotion: Int) {
            view.ivPromotion.setImageResource(promotion)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        val view = ItemListPromotionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PromotionViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        holder.bind(promotions[position])
    }
    
    override fun getItemCount(): Int {
        return promotions.size
    }
}
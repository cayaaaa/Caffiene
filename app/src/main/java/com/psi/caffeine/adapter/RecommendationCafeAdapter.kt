package com.psi.caffeine.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.psi.caffeine.databinding.ItemListCafeHorizontalBinding
import com.psi.caffeine.model.Cafe
import com.psi.caffeine.ui.detail.DetailActivity

class RecommendationCafeAdapter: RecyclerView.Adapter<RecommendationCafeAdapter.RecommendationCafeViewHolder>() {
    
    private val cafeList = mutableListOf<Cafe>()
    
    fun submitList(list: List<Cafe>) {
        cafeList.clear()
        cafeList.addAll(list)
        notifyDataSetChanged()
    }
    
    class RecommendationCafeViewHolder(val view: ItemListCafeHorizontalBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(cafe: Cafe) {
            Glide.with(itemView.context)
                .load(cafe.image)
                .transform(CenterCrop(), RoundedCorners(8))
                .into(view.ivCafe)
            view.tvCafeName.text = cafe.cafeName
            view.tvLocation.text = cafe.location
            view.tvRating.text = cafe.rating.toString()
            view.tvOpenCloseTime.text = "${cafe.openTime} - ${cafe.closeTime}"
            
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_CAFE_ID, cafe.cafeId)
                itemView.context.startActivity(intent)
            }
        }
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationCafeViewHolder {
        val view = ItemListCafeHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationCafeViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: RecommendationCafeViewHolder, position: Int) {
        holder.bind(cafeList[position])
    }
    
    override fun getItemCount(): Int {
        return cafeList.size
    }
    
}
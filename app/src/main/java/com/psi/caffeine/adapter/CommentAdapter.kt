package com.psi.caffeine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psi.caffeine.databinding.ItemListCommentBinding
import com.psi.caffeine.model.Comment

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    
    private val commentList = mutableListOf<Comment>()
    
    fun submitList(list: List<Comment>) {
        commentList.clear()
        commentList.addAll(list)
        notifyDataSetChanged()
    }
    
    class CommentViewHolder(val view: ItemListCommentBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(comment: Comment) {
            view.apply {
                tvUsername.text = comment.username
                tvComment.text = comment.comment
            }
        }
        
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = ItemListCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }
    
    override fun getItemCount(): Int {
        return commentList.size
    }
}
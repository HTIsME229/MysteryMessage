package com.example.mysterymessage.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysterymessage.R

class SkeletonAdapter : RecyclerView.Adapter<SkeletonAdapter.SkeletonViewHolder>() {

    private val skeletonList = List(9) { "skeleton" } // Tạo 9 item giả lập

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skeleton_message, parent, false)
        return SkeletonViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkeletonViewHolder, position: Int) {
        // Không cần bind dữ liệu vì chỉ dùng để hiển thị shimmer
    }

    override fun getItemCount(): Int = skeletonList.size

    class SkeletonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
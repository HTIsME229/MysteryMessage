package com.example.mysterymessage.ui.adapter.FriendAdpater



import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.databinding.ItemContactBinding
import com.example.mysterymessage.databinding.ItemFriendBinding
import com.example.mysterymessage.databinding.ItemFriendRequestBinding

class FriendAdapter(
    private val context: Context
) :RecyclerView.Adapter<FriendAdapter.ViewHolder>() {
    private lateinit var binding: ItemFriendBinding;
    var listFriend: List<User>? = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateListFriend(listUser: List<User>?) {
        listFriend = listUser
        notifyDataSetChanged()

    }

    inner class ViewHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NotifyDataSetChanged")
        fun bindData(user: User) {
            Glide.with(context) // hoặc requireContext() nếu trong Fragment
                .load(user.avatar) // URL ảnh
                .placeholder(R.drawable.avatar1) // Ảnh hiển thị khi đang tải
                .error(R.drawable.avatar1) // Ảnh hiển thị khi lỗi
                .circleCrop() // Cắt ảnh thành hình tròn (tuỳ chọn)
                .into(binding.ivProfile);
            binding.tvName.text = user.userName


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding.root)
    }

    override fun getItemCount(): Int {
        return listFriend?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listFriend?.get(position)?.let { holder.bindData(it) }
    }
}
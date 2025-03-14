package com.example.mysterymessage.ui.adapter.AddFriendAdapter

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

class AddFriendAdapter(
    private  val onItemAddFriendClickListener: OnItemAddFriendClickListener,
private val context: Context
) :RecyclerView.Adapter<AddFriendAdapter.ViewHolder>() {
    private lateinit var binding : ItemContactBinding;
    var listFriendAdd:List<User>? = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    fun updateListFriendAdd(listUser:List<User>){
        listFriendAdd = listUser
        notifyDataSetChanged()

    }
    inner class  ViewHolder(context: Context,itemView:View):RecyclerView.ViewHolder(itemView){
            fun  bindData(user:User){
                Glide.with(context) // hoặc requireContext() nếu trong Fragment
                    .load(user.avatar) // URL ảnh
                    .placeholder(R.drawable.avatar1) // Ảnh hiển thị khi đang tải
                    .error(R.drawable.avatar1) // Ảnh hiển thị khi lỗi
                    .circleCrop() // Cắt ảnh thành hình tròn (tuỳ chọn)
                    .into(binding.ivProfile);
                binding.tvName.text= user.userName
                binding.btnAddFriend.setOnClickListener{
                    onItemAddFriendClickListener.onItemClick(user)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(context,binding.root)
    }

    override fun getItemCount(): Int {
   return  listFriendAdd?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listFriendAdd?.get(position)?.let { holder.bindData(it) }
    }
    interface OnItemAddFriendClickListener {
        fun onItemClick(user: User)
    }
}
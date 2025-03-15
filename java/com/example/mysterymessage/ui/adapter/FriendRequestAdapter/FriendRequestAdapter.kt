package com.example.mysterymessage.ui.adapter.FriendRequestAdapter


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
import com.example.mysterymessage.databinding.ItemFriendRequestBinding

class FriendRequestAdapter(

    private  val onItemRequestFriendClickListener: OnItemRequsestFriendClickListener,
    private val context: Context
) :RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {
    private lateinit var binding : ItemFriendRequestBinding;
    var listFriendRequest:List<User>? = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    fun updateListFriendRequest(listUser:List<User>){
        listFriendRequest = listUser
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

            binding.btnAcceptRequest.setOnClickListener{
                onItemRequestFriendClickListener.onAcceptRequest(user)
            }
            binding.btnCancelRequest.setOnClickListener{
                onItemRequestFriendClickListener.onCancelRequest(user)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFriendRequestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(context,binding.root)
    }

    override fun getItemCount(): Int {
        return  listFriendRequest?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listFriendRequest?.get(position)?.let { holder.bindData(it) }
    }
    interface OnItemRequsestFriendClickListener {
        fun onAcceptRequest(user: User)
        fun onCancelRequest(user: User)
    }
}
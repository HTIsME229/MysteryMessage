package com.example.mysterymessage.ui.chat

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mysterymessage.NavGraphDirections
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.databinding.FragmentChatBinding
import com.example.mysterymessage.ui.chat.adapter.SkeletonAdapter
import com.example.mysterymessage.ui.login.FragmentLogin
import com.example.mysterymessage.ui.login.FragmentLoginDirections
import com.example.mysterymessage.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentChat : Fragment(), MenuProvider {
    private lateinit var mBinding: FragmentChatBinding
    private lateinit var navController: NavController
    private var avatar: ImageView? = null
    private val viewModel: LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentChatBinding.inflate(inflater, container, false)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return mBinding.root
    }
    @SuppressLint("SuspiciousIndentation")
    private fun checkLogin() {
        mBinding.progressBar.visibility=View.VISIBLE
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(viewModel._profile.value == null)
            if(currentUser == null){
                val currentDestination = navController.currentDestination
                Log.d(
                    "NavDebug",
                    "Current Destination: ${currentDestination?.label} (${currentDestination?.id})"
                )
                val action = NavGraphDirections.actionGlobalFragmentLogin2()
                navController.navigate(action)
            }
        else{
            viewModel.refreshUser(currentUser.uid)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        checkLogin()
        mBinding.floatingActionButton.setOnClickListener {
            val action = FragmentChatDirections.actionFragmentChatToFragmentAddFriend2()
            navController.navigate(action)
        }
        viewModel._profile.observe(viewLifecycleOwner) {
            if (it != null)
            {
                updateAvatar()
                mBinding.progressBar.visibility=View.GONE
                }
        }
       val skeletonAdapter = SkeletonAdapter()
        mBinding.recyclerFriendMessageSkeleton.adapter=skeletonAdapter
        mBinding.recyclerFriendMessageSkeleton.visibility = View.VISIBLE // Hiện shimmer

        mBinding.shimmerViewContainer.startShimmer() // Bắt đầu shimmer effect
        mBinding.recyclerFriendMessage.visibility = View.GONE // Ẩn danh sách thật
    }
    private fun updateAvatar() {
        val user = viewModel._profile.value
        if (user != null) {
            avatar?.let { it1 ->
                Glide.with(requireContext()) // hoặc requireContext() nếu trong Fragment
                    .load(user.avatar) // URL ảnh
                    .placeholder(R.drawable.avatar2) // Ảnh hiển thị khi đang tải
                    .error(R.drawable.ic_launcher_background) // Ảnh hiển thị khi lỗi
                    .circleCrop() // Cắt ảnh thành hình tròn (tuỳ chọn)
                    .into(it1)
            };
        } else {
            avatar?.let { it1 ->
                Glide.with(requireContext()) // hoặc requireContext() nếu trong Fragment
                    .load(R.drawable.logout) // URL ảnh
                    .error(R.drawable.ic_launcher_background) //
                    .placeholder(R.drawable.logout) // Ảnh hiển thị khi đang tải
                    .circleCrop() // Cắt ảnh thành hình tròn (tuỳ chọn)
                    .into(it1)
            };
        }

    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_chat_fragment, menu)
        val actionView = menu.findItem(R.id.menu_avatar).actionView
        actionView?.let {
            avatar = it.findViewById(R.id.menu_item_avatar) // Gán avatar đúng cách
        }
        avatar?.setOnClickListener {
            val currentDestination = navController.currentDestination
            Log.d(
                "NavDebug",
                "Current Destination: ${currentDestination?.label} (${currentDestination?.id})"
            )
            val action = FragmentChatDirections.actionFragmentChatToFragmentSettings2()
            navController.navigate(action)
        }
        updateAvatar()


    }
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

}
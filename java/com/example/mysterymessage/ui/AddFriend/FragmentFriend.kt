package com.example.mysterymessage.ui.AddFriend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.databinding.FragmentFriendBinding
import com.example.mysterymessage.ui.adapter.FriendAdpater.FriendAdapter
import com.example.mysterymessage.ui.adapter.FriendRequestAdapter.FriendRequestAdapter
import com.example.mysterymessage.ui.login.LoginViewModel


class FragmentFriend :Fragment(),MenuProvider{
    private lateinit var binding:FragmentFriendBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val friendViewModel: FriendViewModel by activityViewModels()
    private lateinit var friendRequestAdapter:FriendRequestAdapter
    private lateinit var friendAdapter: FriendAdapter
    private lateinit var  swipeRefreshLayout: SwipeRefreshLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
swipeRefreshLayout= binding.swipeRefreshLayout
        friendAdapter= FriendAdapter(requireContext())

        friendRequestAdapter= FriendRequestAdapter(object : FriendRequestAdapter.OnItemRequsestFriendClickListener {
            override fun onAcceptRequest(user: User) {
             friendViewModel.acceptFriendRequest(loginViewModel._profile.value!!.userName,user.userName)
            }

            override fun onCancelRequest(user: User) {
            }

        },requireContext())
        binding.recyclerFriendRequest.adapter = friendRequestAdapter


        loginViewModel._profile.observe(viewLifecycleOwner){
            if(it != null){
                friendViewModel.listFriendRequestByUid(it.uid,false)
                friendViewModel.listFriendByUid(it.uid,false)
            }
        }

        friendViewModel._listFriendRequest.observe(viewLifecycleOwner){
            if(it != null){
                setUpView(it)
                swipeRefreshLayout.isRefreshing=false

            }
        }
        friendViewModel._listFriendAdd.observe(viewLifecycleOwner){
            if(it!= null){
                swipeRefreshLayout.isRefreshing=false
            }
        }

        setUpAction()

    }

    private fun setUpAction() {

        binding.btnFriends.setOnClickListener{

            friendAdapter.updateListFriend(friendViewModel._listFriendAdd.value)
            binding.recyclerFriendRequest.adapter=friendAdapter
        }
        binding.btnRequestFriend.setOnClickListener{
            binding.recyclerFriendRequest.adapter=friendRequestAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            loginViewModel._profile.value?.let {
                friendViewModel.listFriendRequestByUid(it.uid,true)
                friendViewModel.listFriendByUid(it.uid,true )
            }
       }

    }

    private fun setUpView(list: List<User>?) {
        friendRequestAdapter.updateListFriendRequest(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFriendBinding.inflate(inflater,container,false)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return  binding.root
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_add_friend,menu)
        val menuItem = menu.findItem(R.id.search_action)
        val searchView = menuItem.actionView  as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {

                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}
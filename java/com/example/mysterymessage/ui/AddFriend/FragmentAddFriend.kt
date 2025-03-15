package com.example.mysterymessage.ui.AddFriend

import android.os.Bundle
import android.util.Log
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.databinding.FragmentAddFriendBinding
import com.example.mysterymessage.ui.adapter.AddFriendAdapter.AddFriendAdapter
import com.example.mysterymessage.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddFriend:Fragment() ,MenuProvider {
    private lateinit var binding :FragmentAddFriendBinding
    private val viewModel: FriendViewModel by viewModels()
    private lateinit var adapter: AddFriendAdapter
    private val loginViewModel: LoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddFriendBinding.inflate(inflater,container,false)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()

    }

    private fun setUpView() {
            adapter = AddFriendAdapter(loginViewModel._profile.value?:null,object :AddFriendAdapter.OnItemAddFriendClickListener{
                override fun onItemClick(frienduser: User) {
                    if(loginViewModel._profile.value != null)
                    {
                        val user = loginViewModel._profile.value
                        user?.let {
                            viewModel.sendFriendRequest(user.userName,frienduser.userName)
                        }

                    }
                }

            },requireContext())
        viewModel._listFriend.observe(viewLifecycleOwner){
            if (it != null){
                adapter.updateListFriendAdd(it)
            }
        }
        binding.listFriendAdd.adapter=adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().removeMenuProvider(this) // Gỡ MenuProvider khi Fragment bị hủy
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_add_friend,menu)
        val menuItem = menu.findItem(R.id.search_action)
        val searchView = menuItem.actionView  as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchUser(query)
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
package com.salim.finalprojectgitappsalim.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salim.finalprojectgitappsalim.R
import com.salim.finalprojectgitappsalim.activity.DetailActivty
import com.salim.finalprojectgitappsalim.adapter.AdapterUser
import com.salim.finalprojectgitappsalim.databinding.FragmentFollowerBinding
import com.salim.finalprojectgitappsalim.viewmodel.FollowerViewModel


class FragmentFollower : Fragment(R.layout.fragment_follower) {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFollowers : FollowerViewModel
    private lateinit var adapteruser : AdapterUser
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivty.EXTRA_USERNAME).toString()
        _binding = FragmentFollowerBinding.bind(view)
        adapteruser = AdapterUser()
        adapteruser.notifyDataSetChanged()

        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = adapteruser
        }
        showLoading(true)
        viewModelFollowers = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        viewModelFollowers.setListFollowrs(username)
        viewModelFollowers.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null){
                adapteruser.setUserList(it)
                showLoading(false)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showLoading(state: Boolean){
        binding.apply {
            if (state){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }
    }
}
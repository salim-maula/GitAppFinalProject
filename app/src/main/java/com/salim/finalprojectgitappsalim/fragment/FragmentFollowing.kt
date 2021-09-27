package com.salim.finalprojectgitappsalim.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salim.finalprojectgitappsalim.R
import com.salim.finalprojectgitappsalim.activity.DetailActivty
import com.salim.finalprojectgitappsalim.adapter.AdapterUser
import com.salim.finalprojectgitappsalim.databinding.FragmentFollowingBinding
import com.salim.finalprojectgitappsalim.viewmodel.FollowingViewModel

class FragmentFollowing : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFollowing: FollowingViewModel
    private lateinit var adapter : AdapterUser
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivty.EXTRA_USERNAME).toString()

        _binding = FragmentFollowingBinding.bind(view)

        adapter = AdapterUser()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }
        showLoading(true)
        viewModelFollowing = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModelFollowing.setListFollowing(username)
        viewModelFollowing.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setUserList(it)
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
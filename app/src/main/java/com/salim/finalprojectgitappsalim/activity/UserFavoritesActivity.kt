package com.salim.finalprojectgitappsalim.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salim.finalprojectgitappsalim.R
import com.salim.finalprojectgitappsalim.adapter.AdapterUser
import com.salim.finalprojectgitappsalim.database.FavoriteUsers
import com.salim.finalprojectgitappsalim.databinding.ActivityUserFavoritesBinding
import com.salim.finalprojectgitappsalim.response.ModelUser
import com.salim.finalprojectgitappsalim.viewmodel.FavoriteUserViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class UserFavoritesActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUserFavoritesBinding
    private lateinit var vieModel : FavoriteUserViewModel
    private lateinit var userAdapter : AdapterUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = "fovorite user"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        userAdapter = AdapterUser()
        userAdapter.notifyDataSetChanged()
        userAdapter.setOnItemClickCallBack(object : AdapterUser.OnItemClickCallBack{
            override fun onItemClick(data: ModelUser) {
                Intent(this@UserFavoritesActivity, DetailActivty::class.java).also {
                    it.putExtra(DetailActivty.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivty.GITHUB_ID, data.id)
                    it.putExtra(DetailActivty.GITHUB_AVATAR, data.avatar_url)
                    startActivity(it)
                    slideToLeft()
                }
            }

        })


        vieModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)

        vieModel.getListFavoriteUsers()?.observe(this, {
            if (it != null) {
                val mapList = mapList(it)
                userAdapter.setUserList(mapList)
            }
        })

        with(binding) {

            rvUser.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@UserFavoritesActivity)
                adapter = userAdapter

                OverScrollDecoratorHelper.setUpOverScroll(
                    rvUser,
                    OverScrollDecoratorHelper.ORIENTATION_VERTICAL
                )

            }
        }

    }

    private fun mapList(favoriteUsers: List<FavoriteUsers>): ArrayList<ModelUser>  {
        val userList = ArrayList<ModelUser>()
        for (users in favoriteUsers) {
            val mapUser = ModelUser(users.login, users.id,users.type, users.avatar_url)
            userList.add(mapUser)
        }
        return userList
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        slideToRight()
        return super.onSupportNavigateUp()
    }

    private fun slideToLeft() {
        overridePendingTransition(
            R.anim.slide_from_right_animation,
            R.anim.slide_to_left_animation
        )
    }

    private fun slideToRight() {
        overridePendingTransition(
            R.anim.slide_from_left_animation,
            R.anim.slide_to_right_animation
        )
    }
}
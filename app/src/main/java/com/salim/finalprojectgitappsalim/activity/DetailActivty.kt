package com.salim.finalprojectgitappsalim.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.salim.finalprojectgitappsalim.R
import com.salim.finalprojectgitappsalim.adapter.PagerAdapter
import com.salim.finalprojectgitappsalim.databinding.ActivityDetailActivtyBinding
import com.salim.finalprojectgitappsalim.transformpage.AnimateTab
import com.salim.finalprojectgitappsalim.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivty : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TITLE_TABS = intArrayOf(R.string.followers, R.string.following)
        const val GITHUB_ID = "id"
        const val GITHUB_AVATAR = "avatar"
        const val GITHUB_TYPE = "type"
    }

    private lateinit var binding: ActivityDetailActivtyBinding
    private lateinit var vieModel: DetailUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = "detail user"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(GITHUB_ID, 0)
        val avatar = intent.getStringExtra(GITHUB_AVATAR)
        val type = intent.getStringExtra(GITHUB_TYPE)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        vieModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        if (username != null) {
            vieModel.setUserDetail(username)
            vieModel.getUserDetails().observe(this, {
                if (it != null) {
                    binding.apply {
                        if (it.name == null){
                            tvNama.text = getString(R.string.name_not_found)
                        }else{
                            tvNama.text = it.name
                        }
                        tvUsername.text ="@${it.login}"
                        if (it.location == null){
                            tvLocation.text = "-"
                        }else{
                            tvLocation.text = it.location
                        }
                        tvFollwers.text = "${it.followers}"
                        tvFollwing.text = "${it.following}"
                        tvRepos.text = "${it.public_repos}"
                        Glide.with(this@DetailActivty)
                            .load(it.avatar_url)
                            .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_account_circle))
                            .into(img)
                    }
                }
            })

            with(binding){
                var checkFavoriteUser = false
                CoroutineScope(Dispatchers.IO).launch {
                    val countFavoriteUser = vieModel.checkFavoriteUsers(id)
                    withContext(Dispatchers.Main) {
                        if (countFavoriteUser != null) {
                            btnFavorite.apply {
                                if (countFavoriteUser > 0) {
                                    isChecked = true
                                    checkFavoriteUser = true
                                } else {
                                    isChecked = false
                                    checkFavoriteUser = false
                                }
                            }
                        }
                    }
                }

                btnFavorite.setOnClickListener {
                    checkFavoriteUser = !checkFavoriteUser
                    val add = resources.getString(R.string.add_favorite)
                    val remove = resources.getString(R.string.remove_favorite)
                    with(vieModel) {
                        btnFavorite.apply {
                            Log.d("btnFavorite", id.toString())
                            if (checkFavoriteUser) {
                                addFavoriteUsers(username.toString(), id, type.toString(), avatar.toString())
                                Snackbar.make(it, add, Snackbar.LENGTH_SHORT)
                                    .setTextColor(context.getColor(R.color.white))
                                    .setBackgroundTint(context.getColor(R.color.colorDarkGray))
                                    .setActionTextColor(context.getColor(R.color.white))
                                    .setAction(R.string.see) {
                                        val intent = Intent(this@DetailActivty, UserFavoritesActivity::class.java)
                                        startActivity(intent)
                                    }
                                    .show()
                            } else {
                                removeFavoriteUsers(id)
                                Snackbar.make(it, remove, Snackbar.LENGTH_SHORT)
                                    .setTextColor(context.getColor(R.color.white))
                                    .setBackgroundTint(context.getColor(R.color.colorDarkGray))
                                    .show()
                                isChecked = checkFavoriteUser
                            }
                        }
                    }
                }
            }
            val sectionsPagerAdapter = PagerAdapter(this, bundle)
            binding.apply {
                viewPager.adapter = sectionsPagerAdapter
                viewPager.setPageTransformer(AnimateTab())
                TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                    tab.text = resources.getString(TITLE_TABS[position])
                }.attach()
            }
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        slideToRight()
        return super.onSupportNavigateUp()
    }

    private fun slideToRight() {
        overridePendingTransition(
            R.anim.slide_from_left_animation,
            R.anim.slide_to_right_animation
        )
    }
}
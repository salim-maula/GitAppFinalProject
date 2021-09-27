package com.salim.finalprojectgitappsalim.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salim.finalprojectgitappsalim.R
import com.salim.finalprojectgitappsalim.adapter.AdapterUser
import com.salim.finalprojectgitappsalim.databinding.ActivityMainBinding
import com.salim.finalprojectgitappsalim.datastore.SettingPreferences
import com.salim.finalprojectgitappsalim.response.ModelUser
import com.salim.finalprojectgitappsalim.viewmodel.UserViewModel
import com.salim.finalprojectgitappsalim.viewmodel.datastore.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var vieModel : UserViewModel
    private lateinit var userAdapter : AdapterUser
    private lateinit var binding: ActivityMainBinding

    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title="Github User"

        binding.apply {
            bgSearch.visibility = View.VISIBLE
            bgTxtsearch.visibility = View.VISIBLE
        }

        userAdapter = AdapterUser()
        userAdapter.notifyDataSetChanged()
        userAdapter.setOnItemClickCallBack(object : AdapterUser.OnItemClickCallBack{
            override fun onItemClick(data: ModelUser) {
                Intent(this@MainActivity, DetailActivty::class.java).also {
                    it.putExtra(DetailActivty.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivty.GITHUB_ID, data.id)
                    it.putExtra(DetailActivty.GITHUB_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        val pref = SettingPreferences.getInstance(dataStore)
        vieModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            UserViewModel::class.java
        )

        vieModel.getThemeSetting().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = userAdapter

            search.setOnClickListener {
                searcUser()
                binding.apply {
                    bgSearch.visibility = View.GONE
                    bgTxtsearch.visibility = View.GONE
                }
            }

            query.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searcUser()
                    binding.apply {
                        bgSearch.visibility = View.GONE
                        bgTxtsearch.visibility = View.GONE
                    }
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        vieModel.getSearchUser().observe(this,{
            if (it!=null) {
                userAdapter.setUserList(it)
                showLoading(false)
                binding.apply {
                    bgSearch.visibility = View.GONE
                    bgTxtsearch.visibility = View.GONE
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun searcUser() {
        binding.apply {
            val queryKey = query.text.toString()
            if (queryKey.isEmpty()) return
            showLoading(true)
            vieModel.setSearchUser(queryKey)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (isChecked){
            menu.findItem(R.id.darkMode).setIcon(R.drawable.ic_sunny)
        }else{
            menu.findItem(R.id.darkMode).setIcon(R.drawable.ic_darkmode)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.likes -> {
                val intent = Intent(this@MainActivity, UserFavoritesActivity::class.java)
                startActivity(intent)
                slideToLeft()


            }
            R.id.darkMode -> {
                isChecked = !isChecked
                vieModel.saveThemeSetting(isChecked)


            }
            R.id.settings -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                slideToLeft()

            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun slideToLeft() {
        overridePendingTransition(
            R.anim.slide_from_right_animation,
            R.anim.slide_to_left_animation
        )
    }
}
package com.salim.finalprojectgitappsalim.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salim.finalprojectgitappsalim.api.Retrofit
import com.salim.finalprojectgitappsalim.database.FavoriteUsers
import com.salim.finalprojectgitappsalim.database.FavoriteUsersDAO
import com.salim.finalprojectgitappsalim.database.FavoriteUsersDB
import com.salim.finalprojectgitappsalim.response.ModelUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application){

    private var favoriteUsersDAO: FavoriteUsersDAO?
    private var favoriteUsersDB: FavoriteUsersDB? = FavoriteUsersDB.getFavoriteUsersDB(application)

    init {
        favoriteUsersDAO = favoriteUsersDB?.favoriteUsersDAO()
    }

    val users = MutableLiveData<ModelUser>()

    fun setUserDetail(username: String) {
        Retrofit.instance.getDetailUser(username).enqueue(object : Callback<ModelUser> {
            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                if (response.isSuccessful) users.postValue(response.body())
            }

            override fun onFailure(call: Call<ModelUser>, throwable: Throwable) {
                Log.d("Failure ", throwable.message.toString())
            }
        })
    }

    fun getUserDetails(): LiveData<ModelUser> {
        return users
    }

    suspend fun checkFavoriteUsers(id: Int) = favoriteUsersDAO?.checkFavoriteUsers(id)

    fun addFavoriteUsers(username: String, id: Int,type : String, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val githubFavoriteUsers = FavoriteUsers(username,id,type,avatar)
            favoriteUsersDAO?.addFavoriteUsers(githubFavoriteUsers)
            Log.d("Failure ", githubFavoriteUsers.toString())
        }
    }

    fun removeFavoriteUsers(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUsersDAO?.removeFavoriteUsers(id)
            Log.d("Failure ", id.toString())
        }
    }

}
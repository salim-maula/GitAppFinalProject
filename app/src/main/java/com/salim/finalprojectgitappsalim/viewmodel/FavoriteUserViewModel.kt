package com.salim.finalprojectgitappsalim.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salim.finalprojectgitappsalim.database.FavoriteUsers
import com.salim.finalprojectgitappsalim.database.FavoriteUsersDAO
import com.salim.finalprojectgitappsalim.database.FavoriteUsersDB
import com.salim.finalprojectgitappsalim.response.ModelUser

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {
    val users = MutableLiveData<ModelUser>()
    private var favoriteUsersDAO: FavoriteUsersDAO?
    private var favoriteUsersDB: FavoriteUsersDB? = FavoriteUsersDB.getFavoriteUsersDB(application)

    init {
        favoriteUsersDAO = favoriteUsersDB?.favoriteUsersDAO()
    }

    fun getListFavoriteUsers(): LiveData<List<FavoriteUsers>>? {
        return favoriteUsersDAO?.getFavoriteUsers()
    }
    fun getUserDetails(): LiveData<ModelUser> {
        return users
    }

}
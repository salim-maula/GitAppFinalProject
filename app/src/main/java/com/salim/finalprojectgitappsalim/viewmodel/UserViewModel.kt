package com.salim.finalprojectgitappsalim.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.salim.finalprojectgitappsalim.response.ModelUser
import com.salim.finalprojectgitappsalim.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.salim.finalprojectgitappsalim.api.Retrofit
import com.salim.finalprojectgitappsalim.datastore.SettingPreferences
import kotlinx.coroutines.launch

class UserViewModel(private val pref : SettingPreferences) : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<ModelUser>>()

    fun setSearchUser(query: String){
        Retrofit.instance.getSearchUser(query).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    listUsers.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
            }

        })
    }

    fun getSearchUser(): LiveData<ArrayList<ModelUser>>{
        return listUsers
    }

    fun getThemeSetting() : LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive : Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
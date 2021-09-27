package com.salim.finalprojectgitappsalim.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salim.finalprojectgitappsalim.api.Retrofit
import com.salim.finalprojectgitappsalim.response.ModelUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    val listFollowrs = MutableLiveData<ArrayList<ModelUser>>()

    fun setListFollowrs(username: String){
        Retrofit.instance
            .getFollowersUser(username)
            .enqueue(object : Callback<ArrayList<ModelUser>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelUser>>,
                    response: Response<ArrayList<ModelUser>>
                ) {
                    if (response.isSuccessful){
                        listFollowrs.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ModelUser>>, t: Throwable) {
                    t.message?.let { Log.d("onFailure", it) }
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<ModelUser>> {
        return listFollowrs
    }
}
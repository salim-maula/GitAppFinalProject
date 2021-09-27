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

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<ModelUser>>()

    fun setListFollowing(usename: String){
        Retrofit.instance
            .getFollowingUser(usename)
            .enqueue(object : Callback<ArrayList<ModelUser>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelUser>>,
                    response: Response<ArrayList<ModelUser>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ModelUser>>, t: Throwable) {
                    t.message?.let { Log.d("onFailure", it) }
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<ModelUser>> {
        return listFollowing
    }
}
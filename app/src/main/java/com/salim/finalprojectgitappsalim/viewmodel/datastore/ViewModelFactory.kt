package com.salim.finalprojectgitappsalim.viewmodel.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salim.finalprojectgitappsalim.datastore.SettingPreferences
import com.salim.finalprojectgitappsalim.viewmodel.UserViewModel

class ViewModelFactory(private val pref : SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
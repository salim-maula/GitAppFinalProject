package com.salim.finalprojectgitappsalim.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "favorite_users"
)

data class FavoriteUsers(
    val login: String,
    @PrimaryKey
    val id: Int,
    val type : String,
    val avatar_url: String

) : Serializable
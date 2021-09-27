package com.salim.finalprojectgitappsalim.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ModelUser(
    var login: String? = null,
    val id: Int,
    var type: String? = null,
    var avatar_url: String? = null,
    var name: String? = null,
    var location: String? = null,
    var company: String? = null,
    var followers: Int = 0,
    var following: Int = 0,
    var public_repos: Int = 0
): Parcelable

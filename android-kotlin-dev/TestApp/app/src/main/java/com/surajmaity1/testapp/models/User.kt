package com.surajmaity1.testapp.models

data class User(
    val name: String = "",
    val address: String = "",
    val km: Int = 0,
    val invitePending: Boolean = false,
    val img: Int = -1,
    val progress: Int = 0,
    var hobby: ArrayList<String> = ArrayList(),
    val about: String = ""
)

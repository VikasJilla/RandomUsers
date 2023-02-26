package com.example.randomusers.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    val name: UserName?,
    val location: Location?,
    val gender:String,
    val picture: Picture,
    val login: Login,
    val dob:DOB,
    val email:String,
    val cell:String,
    )
data class UserName(
    @SerializedName("first")
    val firstName:String,
    @SerializedName("last")
    val lastName:String,
    val title:String,
)
data class DOB(val date:String)
data class Location(val city:String)
data class Picture(val large:String,val medium:String,val thumbnail:String)
data class Login(val uuid: UUID)
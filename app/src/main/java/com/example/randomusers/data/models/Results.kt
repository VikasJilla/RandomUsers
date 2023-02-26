package com.example.randomusers.data.models

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("results")
    val users:List<User>)

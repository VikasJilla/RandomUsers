package com.example.randomusers.presentation.clicklisteners

import com.example.randomusers.data.entities.UserEntity

interface CustomClickListener {
    fun itemClicked(user: UserEntity?)
}
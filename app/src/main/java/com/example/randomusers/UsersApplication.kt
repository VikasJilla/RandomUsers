package com.example.randomusers

import android.app.Application
import com.example.randomusers.core.NetworkConnection
import com.example.randomusers.db.UsersDatabase
import com.example.randomusers.domain.repositories.UsersRepositoryImpl
import com.example.randomusers.network.RetrofitProvider
import com.example.randomusers.network.services.UsersService

class UsersApplication : Application() {

    private val networkConnection:NetworkConnection = NetworkConnection()

    val usersRepository by lazy { UsersRepositoryImpl(RetrofitProvider.getRetrofit().create(UsersService::class.java),
        networkConnection,UsersDatabase.getDatabase(applicationContext).getUsersDAO()
    ) }
}
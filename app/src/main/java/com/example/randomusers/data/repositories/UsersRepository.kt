package com.example.randomusers.data.repositories

import com.example.randomusers.data.models.RepoResult

abstract class UsersRepository{
    abstract suspend fun fetchUsers(pageNumber:Int): RepoResult
}
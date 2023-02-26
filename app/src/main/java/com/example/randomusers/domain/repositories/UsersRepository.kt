package com.example.randomusers.domain.repositories

import android.util.Log
import com.example.randomusers.core.NetworkConnection
import com.example.randomusers.data.entities.UserEntity
import com.example.randomusers.data.models.*
import com.example.randomusers.data.repositories.UsersRepository
import com.example.randomusers.db.UsersDAO
import com.example.randomusers.network.services.UsersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UsersRepositoryImpl(private val service: UsersService,private val networkConnection: NetworkConnection,private val usersDAO: UsersDAO) : UsersRepository() {
    private val tag = "UsersRepositoryImpl"
    override suspend fun fetchUsers(pageNumber:Int): RepoResult{
        val repoResult = withContext(Dispatchers.IO){
            if(networkConnection.isConnected()){
                Log.d(tag,"is connected")
                val response:Response<Results> = service.getUsers(pageNumber)
                if(response.isSuccessful){
                    val results = response.body()
                    val users = getUserEntities(results?.users?: emptyList())
                    saveUsers(users)
                    if(users.size == 20){
                        users.add(null)
                    }
                    return@withContext ResultSuccess(users)
                }
                return@withContext ResultFailure(ResultError("Something Went Wrong"))
            }else{
                Log.d(tag,"is not connected")
                val users = usersDAO.getAllUsers()
                return@withContext ResultSuccess(users)
            }
        }
        Log.d(tag,"in fetchUsers $repoResult")
        return repoResult
    }

    private fun getUserEntities( users:List<User>):MutableList<UserEntity?>{
        return users.map { user -> UserEntity.createFromUser(user)}.toMutableList()
    }

    private suspend fun saveUsers(users:List<UserEntity?>){
        usersDAO.insert(users)
    }
}
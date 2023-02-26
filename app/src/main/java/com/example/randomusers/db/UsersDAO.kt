package com.example.randomusers.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomusers.data.entities.UserEntity

@Dao
interface UsersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<UserEntity?>)

    @Query("SELECT * FROM Users")
    suspend fun getAllUsers():List<UserEntity>
}
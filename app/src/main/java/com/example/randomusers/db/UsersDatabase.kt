package com.example.randomusers.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.randomusers.data.entities.UserEntity

@Database(entities = [UserEntity::class], exportSchema = true, version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun getUsersDAO():UsersDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        fun getDatabase(context: Context): UsersDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java,
                    "users_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
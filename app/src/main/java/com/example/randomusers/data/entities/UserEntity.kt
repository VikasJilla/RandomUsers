package com.example.randomusers.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.randomusers.data.models.User
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey
    val loginID:UUID,
    val firstName:String?,
    val lastName:String?,
    val title:String?,
    val thumbnail:String?,
    val mediumImage:String?,
    val location:String?,
    val gender:String?,
    val dob: String,
    val email:String,
    val cell:String,): Serializable{
    companion object UserEntityFactory{
        fun createFromUser(user: User):UserEntity{
            return UserEntity(user.login.uuid,
                user.name?.firstName,
                user.name?.lastName,
                user.name?.title,
                user.picture.thumbnail,
                user.picture.medium,
                user.location?.city,
                user.gender,
                user.dob.date,
                user.email,
                user.cell
            )
        }
    }

    fun isMale():Boolean{
        return gender.equals("male")
    }

    fun displayName():String{
        return (title?:"")
            .plus(if(title == null) "" else ".")
            .plus(firstName?:"")
            .plus(if(firstName != null && lastName != null)" " else "")
            .plus(lastName?:"")
            .plus(if(gender == null)"" else " (${gender.substring(0,1).uppercase()})")
    }

    fun getDOB():String{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy",Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        return try {
            dateFormat.parse(dob)?.let { dateFormat.format(it) }?:"NA"
        } catch (e: ParseException) {
            "NA"
        }
    }
}
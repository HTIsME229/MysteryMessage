package com.example.mysterymessage.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "users")
data class User(

    @PrimaryKey
@SerializedName("uid")
    val uid : String = "",
    @SerializedName("userName")

    val userName: String = "",
    @SerializedName( "email")
    val email: String = "",
    @SerializedName( "display_name")
    val displayName:String ="",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("friends")
    val friends :MutableList<String> = mutableListOf(),
    @SerializedName("friendRequests")
    val friendRequests :MutableList<String> = mutableListOf(),

    @SerializedName("token")
    val token:String = ""
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return userName == other.userName
    }

    override fun hashCode(): Int {
        return userName.hashCode()
    }

    override fun toString(): String {
        return "User(userName='$userName', email='$email', displayName='$displayName', " +
                "password='$password', avatar='$avatar', friends=$friends, token='$token')"
    }
}
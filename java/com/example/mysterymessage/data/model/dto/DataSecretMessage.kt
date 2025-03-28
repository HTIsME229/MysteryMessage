package com.example.mysterymessage.data.model.dto

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class DataSecretMessage(
    var message: String = "",
    var title: String = "",
    var token: String = "",
    var userNameSender: String = "",
    @SerializedName("friendUserName")
    var userNameReceiver: String = "",
    var userReceiverId: String = "",
    var sendTime: String = "",
    var status :String = "",
    var dataQuestion: DataQuestion = DataQuestion("","")
):Parcelable
@Parcelize
data class DataQuestion(
    val question:String="",
    val answer:String="",
    val multipleChoice:MultipleChoice?= null,

) : Parcelable{
    companion object {
        fun fromJson(json: String): DataQuestion {
            return Gson().fromJson(json, DataQuestion::class.java)
        }
    }
}
@Parcelize
data class MultipleChoice(
    val choice1:String="",
    val choice2:String="",
    val choice3:String="",
    val choice4:String="",
):Parcelable
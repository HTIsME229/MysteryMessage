package com.example.mysterymessage.data.model.dto

import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage.MutipleChoiceFragment

data class DataSecretMessage(
    var message: String = "",
    var title: String = "",
    var token: String = "",
    var userNameSender: String = "",
    var userReceiverId: String = "",
    var sendTime: String = "",
    var dataQuestion: DataQuestion = DataQuestion("","")
)
data class DataQuestion(
    val question:String,
    val answer:String,
    val multipleChoice:MultipleChoice?= null,
)
data class MultipleChoice(
    val question:String = "",
    val choice1:String="",
    val choice2:String="",
    val choice3:String="",
    val choice4:String="",
    val correctAnswer:String=""
)
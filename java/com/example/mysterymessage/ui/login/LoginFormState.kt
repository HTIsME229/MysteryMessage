package com.example.mysterymessage.ui.login

data  class LoginFormState (
    var userNameError:Int? = null,
    var passwordError:Int? =null,
    var isCorrect:Boolean=false
    )
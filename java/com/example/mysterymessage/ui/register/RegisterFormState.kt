package com.example.mysterymessage.ui.register


data class RegisterFormState (var emailError:Int? = null,
                              var userNameError:Int? =null,
                              var imageError: Int? = null,
                              var confirmPassword:Int? =null,
                              var password:Int?= null,
                              var isCorrect:Boolean=false)


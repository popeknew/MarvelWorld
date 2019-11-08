package com.example.marvelworld.utility

import com.example.marvelworld.ext.isEmail

class VerificationUtils {

    fun isEmailCorrect(text: String) = text.isEmail()

    fun isPasswordCorret(text: String) = text.isNotEmpty()
}
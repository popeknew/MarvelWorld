package com.example.marvelworld.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelworld.ext.empty
import com.example.marvelworld.utility.VerificationUtils

class LoginViewModel(private val verificationUtils: VerificationUtils) : ViewModel() {

    private val _logInData = MutableLiveData<Boolean>(false)
    val logInData: LiveData<Boolean> = _logInData

    var loginInput: String = String.empty
    var passwordInput: String = String.empty

    val loginValidation = ObservableField<Boolean>(true)
    val passwordValidation = ObservableField<Boolean>(true)

    fun logIn() {
        _logInData.value = fieldsValidation()
    }

    private fun fieldsValidation(): Boolean {
        val loginValidation = verificationUtils.isEmailCorrect(loginInput)
        this.loginValidation.set(loginValidation)

        val passwordValidation = verificationUtils.isPasswordCorret(passwordInput)
        this.passwordValidation.set(passwordValidation)

        return loginValidation && passwordValidation
    }

}
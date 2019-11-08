package com.example.marvelworld.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.marvelworld.vm.LoginViewModel
import com.example.marvelworld.R
import com.example.marvelworld.databinding.ActivityLoginBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    private val logInObserver = Observer<Boolean> {isClicked ->
        if (isClicked) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        with(binding) {
            lifecycleOwner = this@LoginActivity
            viewModel = this@LoginActivity.viewModel
        }

        viewModel.logInData.observe(this, logInObserver)
    }
}

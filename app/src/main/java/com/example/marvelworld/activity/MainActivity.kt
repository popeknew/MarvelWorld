package com.example.marvelworld.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelworld.R
import com.example.marvelworld.net.AuthInterceptor
import com.example.marvelworld.repository.RepositoryRetrofit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private val repository: RepositoryRetrofit = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testButton.setOnClickListener {
//            GlobalScope.launch {
////                withContext(Dispatchers.Default) {
////                    val test = repository.getTest()
////                    println("$test -----------------------------------------------------------")
////                }
////            }
        }
    }
}

package com.example.marvelworld.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.marvelworld.R
import com.example.marvelworld.ext.changeToSecureUrl
import com.example.marvelworld.repository.RepositoryRetrofit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.get
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private val repository: RepositoryRetrofit = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var test = ""

        testButton.setOnClickListener {
            GlobalScope.launch {
                withContext(Dispatchers.Default) {
                    test = repository.getAllCharacters().data.results[0].thumbnail.path
                }
            }
        }

        drugiTestButton.setOnClickListener {
            println("${test.changeToSecureUrl()} -----------------------------------------------------------")
            Glide.with(imageTest)
                .load(test.changeToSecureUrl())
                .into(imageTest)
        }

    }
}

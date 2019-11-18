package com.example.marvelworld

import android.app.Application
import com.example.marvelworld.net.AuthInterceptor
import com.example.marvelworld.net.RestApi
import com.example.marvelworld.repository.RepositoryRetrofit
import com.example.marvelworld.utility.VerificationUtils
import com.example.marvelworld.vm.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://gateway.marvel.com/"

class MyApplication: Application() {

    private val appModule = module {
        single { VerificationUtils() }
    }

    private val netModule = module {
       factory { AuthInterceptor() }
        single { provideOkHttpClient(get()) }
        single { provideRetrofit(get()) }
        single { provideRestApi(get()) }
        single { RepositoryRetrofit(get()) }
    }

    private val viewModelModule = module {
        viewModel { LoginViewModel(get()) }
        viewModel { CharactersViewModel() }
        viewModel { ComicsViewModel() }
        viewModel { EventsViewModel() }
        viewModel { HomeViewModel() }
        viewModel { SeriesViewModel() }
        viewModel { StoriesViewModel() }
    }

    private fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient().newBuilder().addInterceptor(authInterceptor).addInterceptor(logging).build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun provideRestApi(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)

    override fun onCreate() {
        super.onCreate()

        val modulesList = listOf(appModule, viewModelModule, netModule)

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(modulesList)
        }
    }
}
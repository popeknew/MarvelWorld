package com.example.marvelworld.net

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

const val API_KEY = "442223e22afd79a3ef948de79a99533d"
const val PUBLIC_KEY = "5e3709823c51dcb4e8ac6d55e0820258"
const val TIME_STAMP = ""


interface RestApi {

    @GET("/v1/public/characters?") //&hash=$API_KEY
    fun getTest() : Deferred<List<String>>
}
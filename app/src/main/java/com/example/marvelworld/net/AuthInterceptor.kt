package com.example.marvelworld.net

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private val TIMESTAMP_KEY = "ts"
    private val HASH_KEY = "hash"
    private val APIKEY_KEY = "apikey"
    private val myPublicKey = "Get your own key from https://developer.marvel.com"
    private val myPrivateKey = "Get your own key from https://developer.marvel.com"

    private val authHashGenerator = AuthHashGenerator()

    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = System.currentTimeMillis()
        var hash = ""
        try {
            hash = authHashGenerator.generateHash(timestamp, myPublicKey, myPrivateKey)
        } catch (e: MarvelApiException) {
            e.printStackTrace()
        }
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter(TIMESTAMP_KEY, timestamp.toString())
            .addQueryParameter(APIKEY_KEY, myPublicKey)
            .addQueryParameter(HASH_KEY, hash)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

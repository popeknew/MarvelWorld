package com.example.marvelworld.net

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private val TIMESTAMP_KEY = "ts"
    private val HASH_KEY = "hash"
    private val APIKEY_KEY = "apikey"
    private val myPublicKey = "5e3709823c51dcb4e8ac6d55e0820258"
    private val myPrivateKey = "41b93668ff874b80a529c4f9fa2150d482e0f0a2"

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
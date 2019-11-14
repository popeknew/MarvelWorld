package com.example.marvelworld.net

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

internal class AuthHashGenerator {

    @Throws(MarvelApiException::class)
    fun generateHash(timestamp: Long, publicKey: String, privateKey: String): String {
        try {
            val md5Encoder = MessageDigest.getInstance("MD5")
            val messageDigest =
                md5Encoder.digest(timestamp.toString().toByteArray() + privateKey.toByteArray() + publicKey.toByteArray())

            val no = BigInteger(1, messageDigest)

            var hashText = no.toString(16)
            while (hashText.length < 32) {
                hashText = "0$hashText"
            }
            return hashText
        } catch (e: NoSuchAlgorithmException) {
            throw MarvelApiException("cannot generate the api key", e)
        }
    }
}
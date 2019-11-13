package com.example.marvelworld.net

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

internal class AuthHashGenerator {

    @Throws(MarvelApiException::class)
    fun generateHash(timestamp: String, publicKey: String, privateKey: String): String {
        try {
            //val value = timestamp + privateKey + publicKey
            val md5Encoder = MessageDigest.getInstance("MD5")
       //     val md5Bytes = md5Encoder.digest(value.toByteArray()).toTypedArray()
            val messageDigest = md5Encoder.digest(timestamp.toByteArray()
            + privateKey.toByteArray()
            + publicKey.toByteArray())

            val no = BigInteger(1, messageDigest)

            var hashText = no.toString(16)
            while (hashText.length < 32) {
                hashText = "0$hashText"
            }

            return hashText

//            val md5 = StringBuilder(md5Bytes.size * 2)
//            for (i in md5Bytes.indices) {
//            //    md5.append(Integer.toHexString(md5Bytes[i] and 0xFF).toInt())
//            }
//
//            return md5.toString()
        } catch (e: NoSuchAlgorithmException) {
            throw MarvelApiException("cannot generate the api key", e)
        }
    }
}
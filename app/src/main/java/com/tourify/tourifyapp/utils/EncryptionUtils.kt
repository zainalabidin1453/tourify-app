package com.tourify.tourifyapp.utils

import android.annotation.SuppressLint
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val AES_MODE = "AES/ECB/PKCS7Padding"
    private const val SECRET_KEY = "3d2ed25fa5c6c8a9f184e22e063f0a12"

    @SuppressLint("GetInstance")
    fun encryptData(data: String): String? {
        try {
            val cipher = Cipher.getInstance(AES_MODE)
            val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            val encryptedBytes = cipher.doFinal(data.toByteArray())
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @SuppressLint("GetInstance")
    fun decryptData(encryptedData: String): String? {
        try {
            val cipher = Cipher.getInstance(AES_MODE)
            val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            val encryptedBytes = Base64.decode(encryptedData, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            return String(decryptedBytes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}

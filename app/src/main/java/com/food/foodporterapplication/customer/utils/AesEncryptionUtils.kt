package com.food.foodporterapplication.customer.utils

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

object AesEncryptionUtils {
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

    fun generateIv(): IvParameterSpec {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return IvParameterSpec(iv)
    }

    fun encrypt(plainText: String, key: SecretKeySpec, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(plainText.toByteArray())
        val combined = iv.iv + cipherText
        return Base64.getEncoder().encodeToString(combined)
    }

    fun returnEncryptedString(plainText: String, keyString: String): String {
        val keySpec = SecretKeySpec(keyString.toByteArray(Charsets.UTF_8), "AES")
        val iv = generateIv()
        return encrypt(plainText, keySpec, iv)
    }
}
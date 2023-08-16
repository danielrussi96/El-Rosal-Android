package com.app.elrosal.utils

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

fun String.encryptPhoneNumber() : String {
    val aesKeyBase64 = "ZM8MZZr++pajlm3U4LEkfsbyL1GB1Ncnx5rvcfseaZQ="
    val aesKeyBytes: ByteArray = Base64.getDecoder().decode(aesKeyBase64)
    val aesKey: ByteArray = aesKeyBytes.copyOf(32)

    val cipherEncrypt = Cipher.getInstance("AES")
    val secretKeySpecEncrypt = SecretKeySpec(aesKey, "AES")
    cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKeySpecEncrypt)

    val encryptedData: ByteArray = cipherEncrypt.doFinal(this.toByteArray())
    return Base64.getEncoder().encodeToString(encryptedData)
}


fun String.decryptedData(): String {
    val aesKeyBase64 = "ZM8MZZr++pajlm3U4LEkfsbyL1GB1Ncnx5rvcfseaZQ="
    val aesKeyBytes: ByteArray = Base64.getDecoder().decode(aesKeyBase64)
    val aesKey: ByteArray = aesKeyBytes.copyOf(32)
    val cipherDecrypt = Cipher.getInstance("AES")
    val secretKeySpecDecrypt = SecretKeySpec(aesKey, "AES")
    cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpecDecrypt)
    val decryptedData: ByteArray = cipherDecrypt.doFinal(Base64.getDecoder().decode(this))
    return String(decryptedData)
}
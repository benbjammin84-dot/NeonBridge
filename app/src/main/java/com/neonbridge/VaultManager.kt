// src/main/java/com/neonbridge/VaultManager.kt

package com.neonbridge

import android.content.Context
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class VaultManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "encrypted_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveFile(key: String, value: ByteArray) {
        val encoded = Base64.encodeToString(value, Base64.DEFAULT)
        sharedPreferences.edit().putString(key, encoded).apply()
    }

    fun loadFile(key: String): ByteArray? {
        val encoded = sharedPreferences.getString(key, null) ?: return null
        return Base64.decode(encoded, Base64.DEFAULT)
    }
}

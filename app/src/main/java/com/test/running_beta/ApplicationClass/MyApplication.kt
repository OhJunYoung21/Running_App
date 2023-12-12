package com.test.running_beta.ApplicationClass

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class MyApplication : Application() {

    lateinit var sharedPreferences: SharedPreferences
        private set

    override fun onCreate() {

        super.onCreate()

        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        sharedPreferences = EncryptedSharedPreferences.create(
            "encryptedLogin",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveLoginStatus(loggedIn: Boolean) {

        val editor = this.sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", loggedIn)
        editor.apply()
    }

    fun saveLoggedInId(userId: String) {

        val editor = this.sharedPreferences.edit()
        editor.putString("loggedInUserId", userId)
        editor.apply()
    }
}
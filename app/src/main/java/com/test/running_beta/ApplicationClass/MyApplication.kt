package com.test.running_beta.ApplicationClass

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.test.running_beta.roomDB.AppDatabase

class MyApplication : Application() {

    lateinit var sharedPreferences: SharedPreferences
        private set

    lateinit var db: AppDatabase

    override fun onCreate() {

        super.onCreate()

        //먼저 Application Class를 생성하고 그 다음 db를 호출한다.Context 오류를 막기 위해서이다.
        db = AppDatabase.getInstance(this)

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
        sharedPreferences.edit().putBoolean("isLoggedIn", loggedIn).apply()
    }

    fun saveLoggedInId(userId: String) {
        val editor = this.sharedPreferences.edit()
        editor.putString("loggedInUserId", userId)
        editor.apply()
    }

    fun successLogin(id: String) {
        Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
        saveLoginStatus(true)
        saveLoggedInId(id)
    }

    fun logOut() {
        saveLoginStatus(false)
        saveLoggedInId("")
    }
}
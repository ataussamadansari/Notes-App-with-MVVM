package com.example.noteapp.Utils

import android.content.Context
import com.example.noteapp.Utils.Constants.PREFS_TOKEN_FILE
import com.example.noteapp.Utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context : Context) {
    private var pref = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)


    fun saveToken(token: String) {
        val editor = pref.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return pref.getString(USER_TOKEN, null)
    }

}
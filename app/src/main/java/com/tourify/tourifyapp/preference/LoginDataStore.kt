package com.tourify.tourifyapp.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tourify.tourifyapp.constants.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFS_LOGIN)

class LoginDataStore private constructor(private val dataStore: DataStore<Preferences>) {
    private object Keys {
        val LOGIN_RESPONSE = stringPreferencesKey(Constants.PREFS_LOGIN)
    }

    companion object {
        private var instance: LoginDataStore? = null

        fun getInstance(context: Context): LoginDataStore {
            return instance ?: synchronized(this) {
                instance ?: LoginDataStore(context.dataStore).also { instance = it }
            }
        }
    }
}

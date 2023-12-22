package com.tourify.tourifyapp.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tourify.tourifyapp.constants.Constants
import com.tourify.tourifyapp.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFS_LOGIN)

class LoginDataStore private constructor(private val dataStore: DataStore<Preferences>) {
    private object Keys {
        val LOGIN_RESPONSE = stringPreferencesKey(Constants.PREFS_LOGIN)
    }

    suspend fun saveLoginStatus(loginResponse: LoginResponse) {
        dataStore.edit { preferences ->
            try {
                preferences[Keys.LOGIN_RESPONSE] = Json.encodeToString(loginResponse)
            } catch (_: Exception) {
            }
        }
    }

    fun getLoginStatus(): Flow<LoginResponse?> {
        return dataStore.data.map { preferences ->
            val loginResponseString = preferences[Keys.LOGIN_RESPONSE]
            loginResponseString?.let { Json.decodeFromString(it) }
        }
    }

    suspend fun deleteLoginStatus() {
        dataStore.edit { preferences ->
            preferences.remove(Keys.LOGIN_RESPONSE)
        }
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

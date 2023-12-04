package com.tourify.tourifyapp.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.tourify.tourifyapp.constants.Constants.PREFS_ONBOARDING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class OnBoardingDataStore private constructor(private val dataStore: DataStore<Preferences>) {
    private object Keys {
        val ONBOARDING_STATUS = booleanPreferencesKey(PREFS_ONBOARDING)
    }
    suspend fun saveOnBoardingStatus(onBoardingStatus: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.ONBOARDING_STATUS] = onBoardingStatus
        }
    }
    fun getOnBoardingStatus(): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[Keys.ONBOARDING_STATUS] ?: false
            }
    }
    companion object {
        private var instance: OnBoardingDataStore? = null
        fun getInstance(context: Context): OnBoardingDataStore {
            return instance ?: synchronized(this) {
                instance ?: OnBoardingDataStore(context.dataStore).also { instance = it }
            }
        }
    }
}


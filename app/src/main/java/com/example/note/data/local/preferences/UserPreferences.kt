package com.example.note.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.note.data.local.model.LoginResponse
import com.example.note.data.local.model.UserData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences @Inject constructor(private val context : Context) {

  private val gson = Gson()

  companion object {

    private val USER_DATA_KEY = stringPreferencesKey("user_data")
    private val TOKEN_KEY = stringPreferencesKey("token")
  }

  suspend fun saveUserSession(loginResponse : LoginResponse) {
    context.dataStore.edit { preferences ->
      preferences[USER_DATA_KEY] = gson.toJson(loginResponse.data)
      preferences[TOKEN_KEY] = loginResponse.token
    }
  }

  suspend fun saveUserToken(token : String) {
    context.dataStore.edit { preferences ->
      preferences[TOKEN_KEY] = token
    }
  }

  fun getUserData() : Flow<UserData?> = context.dataStore.data.map { preferences ->
    preferences[USER_DATA_KEY]?.let { userDataString ->
      gson.fromJson(userDataString, UserData::class.java)
    }
  }

  fun getToken() : Flow<String?> = context.dataStore.data.map { preferences ->
    preferences[TOKEN_KEY]
  }

  suspend fun clearUserSession() {
    context.dataStore.edit { preferences ->
      preferences.clear()
    }
  }
}

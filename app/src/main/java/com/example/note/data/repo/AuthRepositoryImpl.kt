package com.example.note.data.repo

import com.example.note.data.common.ScreenViewState
import com.example.note.data.local.model.*
import com.example.note.data.remote.api.ApiService
import com.example.note.di.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val apiService : ApiService,
  private val userPreferences : UserPreferences
) : AuthRepository, BaseRepository() {


  override suspend fun register(data : RegisterRequest) = flow {
    emit(ScreenViewState.Loading)
    emit(safeApiCall { apiService.register(data) })
  }

  override suspend fun login(data : LoginRequest) = flow {
    emit(ScreenViewState.Loading)
    val result = safeApiCall { apiService.login(data) }
    if (result is ScreenViewState.Success) {
      userPreferences.saveUserSession(result.data)
    }
    emit(result)
  }

  override fun fetchProfileFromApi() : Flow<ScreenViewState<UserLocal>> = flow {
    emit(ScreenViewState.Loading)
    emit(safeApiCall { apiService.getProfile() })
  }


  override fun getUserSession() : Flow<UserData?> = userPreferences.getUserData()

  override suspend fun logout() = flow {
    emit(ScreenViewState.Loading)
    emit(safeApiCall { apiService.logout() })
  }


  override fun refresh() : Flow<ScreenViewState<RefreshResponse>> = flow {
    emit(ScreenViewState.Loading)
    val result = safeApiCall { apiService.refresh() }
    if (result is ScreenViewState.Success) {
      userPreferences.saveUserToken(result.data.token)
    }
    emit(result)
  }
}


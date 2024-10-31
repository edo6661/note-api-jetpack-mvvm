package com.example.note.data.repo

import com.example.note.data.common.ScreenViewState
import com.example.note.data.local.model.*
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

  suspend fun login(
    data : LoginRequest
  ) : Flow<ScreenViewState<LoginResponse>>

  suspend fun register(
    data : RegisterRequest
  ) : Flow<ScreenViewState<RegisterResponse>>

  fun getUserSession() : Flow<UserData?>
  suspend fun logout() : Flow<ScreenViewState<LogoutResponse>>
  fun refresh() : Flow<ScreenViewState<RefreshResponse>>
  fun fetchProfileFromApi() : Flow<ScreenViewState<UserLocal>>


}

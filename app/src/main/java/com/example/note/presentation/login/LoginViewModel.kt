package com.example.note.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.common.ScreenViewState
import com.example.note.data.local.model.LoginRequest
import com.example.note.data.local.model.LoginResponse
import com.example.note.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val authRepository : AuthRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(LoginUiState())
  val uiState get() = _uiState.asStateFlow()

  private fun onUsernameChanged(username : String) {
    _uiState.value = _uiState.value.copy(username = username)
  }

  private fun onPasswordChanged(password : String) {
    _uiState.value = _uiState.value.copy(password = password)
  }

  private fun onEmailChanged(email : String) {
    _uiState.value = _uiState.value.copy(email = email)
  }

  private fun clearFields() {
    _uiState.value = _uiState.value.copy(
      username = "",
      email = "",
      password = ""
    )
  }

  fun onEvent(event : LoginEvent) {
    when (event) {
      is LoginEvent.UsernameChanged -> onUsernameChanged(event.username)
      is LoginEvent.PasswordChanged -> onPasswordChanged(event.password)
      is LoginEvent.EmailChanged    -> onEmailChanged(event.email)
      is LoginEvent.Submit          -> login()
    }
  }

  private fun login() {
    viewModelScope.launch {
      val request = LoginRequest(
        username = uiState.value.username,
        email = uiState.value.email,
        password = uiState.value.password,
      )
      authRepository.login(request)
        .onStart {
          _uiState.update { it.copy(isLoading = true, error = null) }
        }
        .collect { result ->
          _uiState.update {
            when (result) {
              is ScreenViewState.Success -> {
                it.copy(isLoading = false, success = result.data)
              }

              is ScreenViewState.Error   -> it.copy(isLoading = false, error = result.message)
              is ScreenViewState.Loading -> it.copy(isLoading = true)
            }
          }
        }
    }
    clearFields()
  }


}


sealed class LoginEvent {
  data object Submit : LoginEvent()
  data class UsernameChanged(val username : String) : LoginEvent()
  data class EmailChanged(val email : String) : LoginEvent()
  data class PasswordChanged(val password : String) : LoginEvent()
}

data class LoginUiState(
  val isLoading : Boolean = false,
  val error : String? = null,
  val success : LoginResponse? = null,
  val username : String = "",
  val email : String = "",
  val password : String = ""
)
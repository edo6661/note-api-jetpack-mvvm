package com.example.note.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.common.ScreenViewState
import com.example.note.data.local.model.RegisterRequest
import com.example.note.data.local.model.RegisterResponse
import com.example.note.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
  private val authRepository : AuthRepository
) : ViewModel() {


  private val _uiState = MutableStateFlow(RegisterUiState())
  val uiState : StateFlow<RegisterUiState> = _uiState.asStateFlow()


  private fun onUsernameChange(newUsername : String) {
    _uiState.value = _uiState.value.copy(username = newUsername)
  }

  private fun onEmailChange(newEmail : String) {
    _uiState.value = _uiState.value.copy(email = newEmail)
  }

  private fun onPasswordChange(newPassword : String) {
    _uiState.value = _uiState.value.copy(password = newPassword)
  }


  fun onEvent(event : RegisterEvent) {
    when (event) {
      is RegisterEvent.UsernameChanged -> onUsernameChange(event.value)
      is RegisterEvent.EmailChanged    -> onEmailChange(event.value)
      is RegisterEvent.PasswordChanged -> onPasswordChange(event.value)
      is RegisterEvent.Submit          -> register()
    }
  }

  private fun register() {
    viewModelScope.launch {
      val request = RegisterRequest(
        username = _uiState.value.username,
        email = _uiState.value.email,
        password = _uiState.value.password
      )

      authRepository.register(request)
        .onStart {
          _uiState.update { it.copy(isLoading = true, error = null) }
        }
        .collect { result ->
          _uiState.update {
            when (result) {
              is ScreenViewState.Success -> it.copy(
                isLoading = false,
                success = result.data
              )

              is ScreenViewState.Error   -> it.copy(
                isLoading = false,
                error = result.message
              )

              is ScreenViewState.Loading -> it.copy(isLoading = true)

            }
          }
        }
    }
  }
}

data class RegisterUiState(
  val isLoading : Boolean = false,
  val error : String? = null,
  val success : RegisterResponse? = null,
  val username : String = "",
  val email : String = "",
  val password : String = ""
)

sealed class RegisterEvent {

  data class UsernameChanged(val value : String) : RegisterEvent()
  data class EmailChanged(val value : String) : RegisterEvent()
  data class PasswordChanged(val value : String) : RegisterEvent()
  data object Submit : RegisterEvent()
}

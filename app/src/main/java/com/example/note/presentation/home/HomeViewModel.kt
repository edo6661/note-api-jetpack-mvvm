package com.example.note.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.common.ScreenViewState
import com.example.note.data.local.model.NoteLocal
import com.example.note.data.local.model.UserLocal
import com.example.note.data.remote.use_cases.notes.FetchNotesCase
import com.example.note.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val fetchNotesCase : FetchNotesCase,
  private val authRepository : AuthRepository
) : ViewModel() {

  companion object {

    private const val TAG = "HomeViewModel"
  }


  private val _state = MutableStateFlow(HomeUiState())
  val state get() = _state.asStateFlow()

  init {
    fetchNotes()
  }

  fun fetchProfile() {
    authRepository.fetchProfileFromApi()
      .onEach { result ->
        when (result) {
          is ScreenViewState.Loading -> {
            _state.update {
              it.copy(
                isLoading = true,
                error = null,
              )
            }
          }

          is ScreenViewState.Success -> {
            _state.update {
              it.copy(
                isLoading = false,
                user = result.data
              )
            }
          }

          is ScreenViewState.Error   -> {
            _state.update {
              it.copy(
                isLoading = false,
                error = result.message
              )
            }
          }
        }
      }
      .catch { exception ->
        _state.update {
          it.copy(
            isLoading = false,
            error = exception.message
          )
        }
      }
      .launchIn(viewModelScope)
  }

  private fun fetchNotes() {
    fetchNotesCase().onEach { result ->
      when (result) {
        is ScreenViewState.Loading -> {
          _state.update {
            it.copy(
              isLoading = true,
              error = null,
            )
          }
        }

        is ScreenViewState.Success -> {
          _state.update {
            it.copy(
              isLoading = false,
              notes = result.data
            )
          }
        }

        is ScreenViewState.Error   -> {
          _state.update {
            it.copy(
              isLoading = false,
              error = result.message
            )
          }
        }
      }
    }
      .catch { exception ->
        _state.update {
          it.copy(
            isLoading = false,
            error = exception.message
          )
        }
      }
      .launchIn(viewModelScope)
  }
}

data class HomeUiState(
  val isLoading : Boolean = false,
  val error : String? = null,
  val notes : NoteLocal? = null,
  val user : UserLocal? = null,
)
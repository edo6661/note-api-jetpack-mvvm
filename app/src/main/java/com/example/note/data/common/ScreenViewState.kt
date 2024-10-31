package com.example.note.data.common

sealed class ScreenViewState<out T> {
  data object Loading : ScreenViewState<Nothing>()
  data class Success<T>(val data : T) : ScreenViewState<T>()
  data class Error(val message : String?) : ScreenViewState<Nothing>()
}
package com.example.note.data.repo

import com.example.note.data.common.ScreenViewState
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository {


  protected suspend fun <T> safeApiCall(
    apiCall : suspend () -> Response<T>
  ) : ScreenViewState<T> {
    return try {
      val response = apiCall()
      if (response.isSuccessful) {
        response.body()?.let {
          ScreenViewState.Success(it)
        } ?: ScreenViewState.Error("Empty response")
      } else {
        val errorBody = response.errorBody()?.string()
        val errorMessage = parseErrorMessage(errorBody)
        ScreenViewState.Error(errorMessage)
      }
    } catch (e : Exception) {
      ScreenViewState.Error(e.message ?: "Unknown error")
    }
  }


  private fun parseErrorMessage(errorBody : String?) : String {
    return try {
      JSONObject(errorBody ?: "").getString("message")
    } catch (e : Exception) {

      "Unknown error occurred"
    }
  }
}

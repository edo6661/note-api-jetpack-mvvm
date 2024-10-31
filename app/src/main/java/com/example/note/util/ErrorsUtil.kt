package com.example.note.util

import org.json.JSONObject

abstract class ErrorsUtil {

  fun parseErrorMessage(errorBody : String?) : String {
    return try {
      JSONObject(errorBody ?: "").getString("message")
    } catch (e : Exception) {
      "Unknown error occurred"
    }
  }


}
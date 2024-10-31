package com.example.note.data.local.interceptors

import com.example.note.di.UserPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
  private val userPreferences : UserPreferences
) : Interceptor {

  override fun intercept(chain : Interceptor.Chain) : Response {
    val originalRequest = chain.request()
    val token = runBlocking {
      userPreferences.getToken().firstOrNull()
    }
    return if (! token.isNullOrEmpty()) {
      val newRequest = originalRequest.newBuilder()
        .header("Authorization", "Bearer $token")
        .build()
      chain.proceed(newRequest)
    } else {
      chain.proceed(originalRequest)
    }
  }
}

package com.example.note.di

import android.content.Context
import com.example.note.BuildConfig
import com.example.note.data.local.interceptors.AuthInterceptor
import com.example.note.data.remote.api.ApiConfig
import com.example.note.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(authInterceptor : AuthInterceptor) : OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(
        if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
      )
      .addInterceptor(authInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(okHttpClient : OkHttpClient) : ApiService {
    val retrofit = Retrofit.Builder()
      .baseUrl(ApiConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
    return retrofit.create(ApiService::class.java)
  }

  @Provides
  @Singleton
  fun provideUserPreferences(@ApplicationContext context : Context) : UserPreferences {
    return UserPreferences(context)
  }


}



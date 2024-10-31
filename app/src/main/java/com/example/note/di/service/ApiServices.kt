package com.example.note.di.service


import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApiService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainApiService

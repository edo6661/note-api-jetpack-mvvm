package com.example.note.di

import com.example.note.data.repo.AuthRepository
import com.example.note.data.repo.AuthRepositoryImpl
import com.example.note.data.repo.NoteRepository
import com.example.note.data.repo.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  @Singleton
  abstract fun bindNoteRepository(
    noteRepositoryImpl : NoteRepositoryImpl
  ) : NoteRepository

  @Binds
  @Singleton
  abstract fun bindAuthRepository(
    authRepositoryImpl : AuthRepositoryImpl
  ) : AuthRepository


}
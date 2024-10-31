package com.example.note.data.repo

import com.example.note.data.common.ScreenViewState
import com.example.note.data.local.model.NoteLocal
import com.example.note.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
  private val apiService : ApiService
) : NoteRepository, BaseRepository() {

  override fun fetchNotesFromApi() :
    Flow<ScreenViewState<NoteLocal>> = flow {
    emit(ScreenViewState.Loading)
    emit(safeApiCall { apiService.fetchNotes() })

  }

  override fun fetchNoteFromApi(id : String) = flow {
    emit(ScreenViewState.Loading)
    emit(safeApiCall { apiService.fetchNote(id) })
  }

}
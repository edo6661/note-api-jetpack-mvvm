package com.example.note.data.repo

import com.example.note.data.common.ScreenViewState
import com.example.note.data.local.model.DataNote
import com.example.note.data.local.model.NoteLocal
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

  fun fetchNotesFromApi() : Flow<ScreenViewState<NoteLocal>>
  fun fetchNoteFromApi(id : String) : Flow<ScreenViewState<DataNote>>

}
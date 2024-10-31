package com.example.note.data.remote.use_cases.notes

import com.example.note.data.repo.NoteRepositoryImpl
import javax.inject.Inject

class FetchNotesCase @Inject constructor(
  private val repo : NoteRepositoryImpl
) {

  operator fun invoke() = repo.fetchNotesFromApi()
}
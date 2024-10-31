package com.example.note.presentation.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.note.ui.shared.CircularCenteredProgressIndicator

@Composable
fun NoteDetailScreen(
  assistedFactory : NoteDetailAssistedFactory,
  id : String,
  onBackClick : () -> Unit
) {

  val vm = viewModel(
    modelClass = NoteDetailViewModel::class.java,
    factory = NoteDetailViewModelFactory(id, assistedFactory)
  )

  val state by vm.state.collectAsState()

  if (state.isLoading) {
    CircularCenteredProgressIndicator()
  } else {
    Text("Note ${state.note?.title}")
  }


}
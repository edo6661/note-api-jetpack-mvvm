package com.example.note.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.note.ui.shared.CircularCenteredProgressIndicator
import com.example.note.ui.shared.ErrorText

@Composable
fun HomeScreen(
  vm : HomeViewModel,
  onNoteClick : (String) -> Unit
) {
  val state by vm.state.collectAsState()
  val notes = state.notes?.data

  if (state.error != null) {
    ErrorText(
      state.error !!
    )
  }
  if (state.isLoading) {
    CircularCenteredProgressIndicator()
  } else if (! notes.isNullOrEmpty()) {
    LazyVerticalStaggeredGrid(
      columns = StaggeredGridCells.Fixed(2),
      modifier = Modifier
        .padding(
          WindowInsets.statusBars.asPaddingValues()
        )
    ) {
      itemsIndexed(notes) { index, note ->
        Card(
          modifier = Modifier.padding(8.dp),
          onClick = { onNoteClick(note.id) }
        ) {
          Text(
            text = note.title
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = note.content,
            maxLines = 3
          )
        }
      }
    }
  }
}

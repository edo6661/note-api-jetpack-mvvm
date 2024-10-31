package com.example.note.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.note.data.common.ScreenViewState
import com.example.note.data.local.model.DataNote
import com.example.note.data.repo.NoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*


data class UiStateNoteDetail(
  val note : DataNote? = null,
  val isLoading : Boolean = false,
  val error : String? = null
)

@AssistedFactory
interface NoteDetailAssistedFactory {

  fun create(id : String) : NoteDetailViewModel
}

@Suppress("UNCHECKED_CAST")
class NoteDetailViewModelFactory(
  private val id : String,
  private val assistedFactory : NoteDetailAssistedFactory
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass : Class<T>) : T {
    return assistedFactory.create(id) as T
  }
}


class NoteDetailViewModel @AssistedInject constructor(
  private val noteRepository : NoteRepository,
  @Assisted private val id : String

) : ViewModel() {


  private val _state = MutableStateFlow(UiStateNoteDetail())
  val state : StateFlow<UiStateNoteDetail> = _state.asStateFlow()

  init {
    fetchNoteDetail()
  }


  private fun fetchNoteDetail() {
    noteRepository.fetchNoteFromApi(
      id = id
    )
      .onStart { _state.update { it.copy(isLoading = true) } }
      .onEach { result ->
        when (result) {
          is ScreenViewState.Loading -> _state.update { it.copy(isLoading = true) }
          is ScreenViewState.Success -> _state.update {
            it.copy(
              note = result.data,
              isLoading = false
            )
          }

          is ScreenViewState.Error   -> _state.update {
            it.copy(
              error = result.message,
              isLoading = false
            )
          }
        }
      }
      .onCompletion { _state.update { it.copy(isLoading = false) } }
      .launchIn(viewModelScope)
  }

  fun retry() {
    _state.update {
      it.copy(
        error = null
      )
    }
    fetchNoteDetail()
  }

}



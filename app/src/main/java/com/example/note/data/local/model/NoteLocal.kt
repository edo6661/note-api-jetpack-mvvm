package com.example.note.data.local.model

data class NoteLocal(
  val data : List<DataNote>,
  val paginationState : PaginationState,
  val additionalInfo : AdditionalInfo
)

data class SingleNoteLocal(
  val data : DataNote
)

data class AdditionalInfo(
  val orderAvailable : List<String>,
  val categoryAvailable : List<String>,
  val category : String,
  val order : String
)

data class DataNote(
  val isArchived : Boolean,
  val isPrivate : Boolean,
  val title : String,
  val userId : String,
  val content : String,
  val downvotedCount : Int,
  val createdAt : String,
  val isDeleted : Boolean,
  val noteAttachments : List<NoteAttachmentsItem>,
  val id : String,
  val upvotedCount : Int,
  val savedCount : Int,
  val updatedAt : String
)

data class NoteAttachmentsItem(
  val id : String,
  val url : String
)

data class PaginationState(
  val hasPrevPage : Boolean,
  val totalData : Int,
  val startIndex : Int,
  val hasNextPage : Boolean,
  val dataPerpage : Int,
  val endIndex : Int,
  val totalPages : Int,
  val currentPage : Int
)


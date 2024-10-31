package com.example.note.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteNoteResponse(

  @field:SerializedName("data")
  val data : List<RemoteNote>,

  @field:SerializedName("paginationState")
  val paginationState : RemotePaginationState,

  @field:SerializedName("additionalInfo")
  val additionalInfo : RemoteAdditionalInfo


)

data class RemoteNoteAttachmentsItem(

  @field:SerializedName("id")
  val id : String,

  @field:SerializedName("url")
  val url : String
)

data class RemotePaginationState(

  @field:SerializedName("hasPrevPage")
  val hasPrevPage : Boolean,

  @field:SerializedName("totalData")
  val totalData : Int,

  @field:SerializedName("startIndex")
  val startIndex : Int,

  @field:SerializedName("hasNextPage")
  val hasNextPage : Boolean,

  @field:SerializedName("dataPerpage")
  val dataPerpage : Int,

  @field:SerializedName("endIndex")
  val endIndex : Int,

  @field:SerializedName("totalPages")
  val totalPages : Int,

  @field:SerializedName("currentPage")
  val currentPage : Int
)

data class RemoteNote(

  @field:SerializedName("isArchived")
  val isArchived : Boolean,

  @field:SerializedName("isPrivate")
  val isPrivate : Boolean,

  @field:SerializedName("title")
  val title : String,

  @field:SerializedName("userId")
  val userId : String,

  @field:SerializedName("content")
  val content : String,

  @field:SerializedName("downvotedCount")
  val downvotedCount : Int,

  @field:SerializedName("createdAt")
  val createdAt : String,

  @field:SerializedName("isDeleted")
  val isDeleted : Boolean,

  @field:SerializedName("noteAttachments")
  val noteAttachments : List<RemoteNoteAttachmentsItem>,

  @field:SerializedName("id")
  val id : String,

  @field:SerializedName("upvotedCount")
  val upvotedCount : Int,

  @field:SerializedName("savedCount")
  val savedCount : Int,

  @field:SerializedName("updatedAt")
  val updatedAt : String


)

data class RemoteAdditionalInfo(

  @field:SerializedName("orderAvailable")
  val orderAvailable : List<String>,

  @field:SerializedName("categoryAvailable")
  val categoryAvailable : List<String>,

  @field:SerializedName("category")
  val category : String,

  @field:SerializedName("order")
  val order : String
)

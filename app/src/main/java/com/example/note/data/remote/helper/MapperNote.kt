package com.example.note.data.remote.helper

import com.example.note.data.local.model.*
import com.example.note.data.remote.model.RemoteNoteResponse


fun RemoteNoteResponse.toLocal() =
  NoteLocal(
    data = data.map {
      DataNote(
        isArchived = it.isArchived,
        isPrivate = it.isPrivate,
        title = it.title,
        userId = it.userId,
        content = it.content,
        downvotedCount = it.downvotedCount,
        createdAt = it.createdAt,
        isDeleted = it.isDeleted,
        noteAttachments = it.noteAttachments.map { attachment ->
          NoteAttachmentsItem(
            id = attachment.id,
            url = attachment.url
          )
        },
        id = it.id,
        upvotedCount = it.upvotedCount,
        savedCount = it.savedCount,
        updatedAt = it.updatedAt
      )
    },
    paginationState = PaginationState(
      hasPrevPage = paginationState.hasPrevPage,
      totalData = paginationState.totalData,
      startIndex = paginationState.startIndex,
      hasNextPage = paginationState.hasNextPage,
      dataPerpage = paginationState.dataPerpage,
      endIndex = paginationState.endIndex,
      totalPages = paginationState.totalPages,
      currentPage = paginationState.currentPage
    ),
    additionalInfo = AdditionalInfo(
      orderAvailable = additionalInfo.orderAvailable,
      categoryAvailable = additionalInfo.categoryAvailable,
      category = additionalInfo.category,
      order = additionalInfo.order
    )
  )

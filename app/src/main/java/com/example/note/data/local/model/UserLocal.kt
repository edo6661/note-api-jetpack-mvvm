package com.example.note.data.local.model

import kotlinx.serialization.Serializable


@Serializable
data class UserLocal(
  val id : String,
  val username : String,
  val email : String,
  val role : String,
  val isOauth : Boolean,
  val lastLogin : String?,
  val isVerified : Boolean,
  val createdAt : String,
  val updatedAt : String
)

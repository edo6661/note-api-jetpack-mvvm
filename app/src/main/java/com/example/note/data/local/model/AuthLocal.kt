package com.example.note.data.local.model

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
  val username : String,
  val email : String,
  val password : String
)

@Serializable
data class RegisterRequest(
  val username : String,
  val email : String,
  val password : String
)

@Serializable
data class LoginResponse(
  val message : String,
  val data : UserData,
  val token : String
)

@Serializable
data class UserData(
  val id : String,
  val username : String,
  val email : String,
  val role : String,
  val isOauth : Boolean,
  val lastLogin : String,
  val profileId : String?
)

@Serializable
data class RegisterResponse(
  val message : String,
  val data : UserData? = null
)

@Serializable
data class LogoutResponse(
  val message : String
)

@Serializable
data class RefreshResponse(
  val message : String,
  val token : String
)

package com.example.note.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginRemote(

  @field:SerializedName("data")
  val data : DataLoginRemote? = null,

  @field:SerializedName("message")
  val message : String? = null,

  @field:SerializedName("token")
  val token : String? = null
)

data class DataLoginRemote(

  @field:SerializedName("lastLogin")
  val lastLogin : String? = null,

  @field:SerializedName("role")
  val role : String? = null,

  @field:SerializedName("profileId")
  val profileId : String? = null,

  @field:SerializedName("id")
  val id : String? = null,

  @field:SerializedName("email")
  val email : String? = null,

  @field:SerializedName("username")
  val username : String? = null,

  @field:SerializedName("isOauth")
  val isOauth : Boolean? = null
)
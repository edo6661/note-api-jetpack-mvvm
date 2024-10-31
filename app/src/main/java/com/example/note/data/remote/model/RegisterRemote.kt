package com.example.note.data.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterRemote(

  @field:SerializedName("data")
  val data : DataRegisterRemote? = null,

  @field:SerializedName("message")
  val message : String? = null
)

data class DataRegisterRemote(

  @field:SerializedName("password")
  val password : String? = null,

  @field:SerializedName("role")
  val role : String? = null,

  @field:SerializedName("email")
  val email : String? = null,

  @field:SerializedName("username")
  val username : String? = null,

  @field:SerializedName("isOauth")
  val isOauth : Boolean? = null
)
package com.example.note.data.remote.api

data object ApiConfig {

  const val BASE_URL = "https://magic-scroll-v2.netlify.app/api/"

  //  note
  const val NOTES = "notes"
  const val NOTE = "notes/{id}"

  //  user
  const val USERS = "users"

  const val PROFILE = "$USERS/profile"

  //  auth
  const val AUTH = "auth/"
  const val LOGIN = AUTH + "login"
  const val REGISTER = AUTH + "register"
  const val LOGOUT = AUTH + "logout"
  const val REFRESH = AUTH + "refresh"
}
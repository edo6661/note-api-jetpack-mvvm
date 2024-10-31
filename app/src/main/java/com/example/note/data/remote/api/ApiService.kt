package com.example.note.data.remote.api

import com.example.note.constant.NoteCategories
import com.example.note.constant.NoteOrders
import com.example.note.data.local.model.*
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

  @GET(ApiConfig.NOTES)
  suspend fun fetchNotes(
    @Query("page") page : String = "1",
    @Query("limit") limit : String = "10",
    @Query("category") category : NoteCategories = NoteCategories.home,
    @Query("order") order : NoteOrders = NoteOrders.new
  ) : Response<NoteLocal>

  @GET(ApiConfig.NOTE)
  suspend fun fetchNote(
    @Path("id") id : String
  ) : Response<DataNote>

  //  auth
  @POST(ApiConfig.REGISTER)
  suspend fun register(
    @Body registerRequest : RegisterRequest
  ) : Response<RegisterResponse>

  @POST(ApiConfig.LOGIN)
  suspend fun login(
    @Body loginRequest : LoginRequest

  ) : Response<LoginResponse>

  @POST(ApiConfig.LOGOUT)
  suspend fun logout() : Response<LogoutResponse>

  @POST(ApiConfig.REFRESH)
  suspend fun refresh() : Response<RefreshResponse>

  //  user
  @GET(ApiConfig.PROFILE)
  suspend fun getProfile() : Response<UserLocal>


}
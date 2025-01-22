package com.example.finalprojectpam.service_api

import com.example.finalprojectpam.model.AllEventResponse
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.EventDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface EventService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("event/store")
    suspend fun insertEvent(@Body event: Event)

    @GET("event")
    suspend fun getAllEvent(): AllEventResponse

    @GET("event/{id_event}")
    suspend fun getEventbyidEvent(@Path("id_event") id_event: Int): EventDetailResponse

    @PUT("event/{id_event}")
    suspend fun updateEvent(@Path("id_event") id_event: Int, @Body event: Event)

    @DELETE("event/{id_event}")
    suspend fun deleteEvent(@Path("id_event") id_event: Int): Response<Void>

}
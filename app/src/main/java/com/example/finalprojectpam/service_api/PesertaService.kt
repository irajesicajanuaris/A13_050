package com.example.finalprojectpam.service_api

import com.example.finalprojectpam.model.AllPesertaResponse
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.model.PesertaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface PesertaService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("peserta/store")
    suspend fun insertPeserta(@Body peserta: Peserta)

    @GET("peserta")
    suspend fun getAllPeserta(): AllPesertaResponse

    @GET("peserta/{id_peserta}")
    suspend fun getPesertabyidPeserta(@Path("id_peserta") id_peserta: Int): PesertaDetailResponse

    @PUT("peserta/{id_peserta}")
    suspend fun updatePeserta(@Path("id_peserta") id_peserta: Int, @Body peserta: Peserta)

    @DELETE("peserta/{id_peserta}")
    suspend fun deletePeserta(@Path("id_peserta") id_peserta: Int): Response<Void>

}
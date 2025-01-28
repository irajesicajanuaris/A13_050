package com.example.finalprojectpam.service_api

import com.example.finalprojectpam.model.AllTransaksiResponse
import com.example.finalprojectpam.model.Transaksi
import com.example.finalprojectpam.model.TransaksiDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TransaksiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("transaksi/store")
    suspend fun insertTransaksi(@Body transaksi: Transaksi)

    @GET("transaksi")
    suspend fun getAllTransaksi(): AllTransaksiResponse
    @GET("transaksi/{id_transaksi}")
    suspend fun getTransaksibyidTransaksi(@Path("id_transaksi") id_transaksi: Int): TransaksiDetailResponse

    @PUT("transaksi/{id_transaksi}")
    suspend fun updateTransaksi(@Path("id_transaksi") id_transaksi: Int, @Body transaksi: Transaksi)

    @DELETE("transaksi/{id_transaksi}")
    suspend fun deleteTransaksi(@Path("id_transaksi") id_transaksi: Int): Response<Void>

}

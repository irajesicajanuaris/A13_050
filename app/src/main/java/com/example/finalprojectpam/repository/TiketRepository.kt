package com.example.finalprojectpam.repository

import com.example.finalprojectpam.model.AllPesertaResponse
import com.example.finalprojectpam.model.AllTiketResponse
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.service_api.TiketService
import okio.IOException


interface TiketRepository {

    suspend fun insertTiket(tiket: Tiket)
    suspend fun getTiket(): AllTiketResponse
    suspend fun updateTiket(id_tiket: Int, tiket: Tiket)
    suspend fun deleteTiket(id_tiket: Int)
    suspend fun getTiketbyidTiket(id_tiket: Int): Tiket
}

class NetworkTiketRepository(
    private val tiketApiService: TiketService
) : TiketRepository {
    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(id_tiket: Int, tiket: Tiket) {
        tiketApiService.updateTiket(id_tiket, tiket)
    }

    override suspend fun getTiket(): AllTiketResponse {
        return tiketApiService.getAllTiket()
    }

    override suspend fun deleteTiket(id_tiket: Int) {
        try {
            val response = tiketApiService.deleteTiket(id_tiket)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete kontak. HTTP Status code: " +
                            "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTiketbyidTiket(id_tiket: Int): Tiket {
        return tiketApiService.getTiketbyidTiket(id_tiket).data
    }
}
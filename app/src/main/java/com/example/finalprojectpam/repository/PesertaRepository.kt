package com.example.finalprojectpam.repository

import com.example.finalprojectpam.model.AllPesertaResponse
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.service_api.PesertaService
import okio.IOException

interface PesertaRepository {
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun getPeserta():AllPesertaResponse
    suspend fun updatePeserta(id_peserta: Int, peserta: Peserta)
    suspend fun deletePeserta(id_peserta: Int)
    suspend fun getPesertabyidPeserta(id_peserta: Int): Peserta
}

class NetworkPesertaRepository(
    private val pesertaApiService: PesertaService
) : PesertaRepository {

    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaApiService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(id_peserta: Int, peserta: Peserta) {
        pesertaApiService.updatePeserta(id_peserta, peserta)
    }

    override suspend fun getPeserta(): AllPesertaResponse {
        return pesertaApiService.getAllPeserta()
    }

    override suspend fun deletePeserta(id_peserta: Int) {
        try {
            val response = pesertaApiService.deletePeserta(id_peserta)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete kontak. HTTP Status code: " + "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPesertabyidPeserta(id_peserta: Int): Peserta {
        return pesertaApiService.getPesertabyidPeserta(id_peserta).data
    }
}

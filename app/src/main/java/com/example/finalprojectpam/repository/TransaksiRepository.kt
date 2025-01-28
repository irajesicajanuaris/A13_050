package com.example.finalprojectpam.repository

import com.example.finalprojectpam.model.AllTransaksiResponse
import com.example.finalprojectpam.model.Transaksi
import com.example.finalprojectpam.service_api.TransaksiService
import okio.IOException

interface TransaksiRepository {

    suspend fun insertTransaksi(transaksi: Transaksi)
    suspend fun getTransaksi(): AllTransaksiResponse
    suspend fun updateTransaksi(id_transaksi: Int, transaksi: Transaksi)
    suspend fun deleteTransaksi(id_transaksi: Int)
    suspend fun getTransaksibyidTransaksi(id_transaksi: Int): Transaksi
}

class NetworkTransaksiRepository(
    private val transaksiApiService: TransaksiService
) : TransaksiRepository {
    override suspend fun insertTransaksi(transaksi: Transaksi) {
        transaksiApiService.insertTransaksi(transaksi)
    }

    override suspend fun updateTransaksi(id_transaksi: Int, transaksi: Transaksi) {
        transaksiApiService.updateTransaksi(id_transaksi, transaksi)
    }

    override suspend fun getTransaksi(): AllTransaksiResponse {
        return transaksiApiService.getAllTransaksi()
    }

    override suspend fun deleteTransaksi(id_transaksi: Int) {
        try {
            val response = transaksiApiService.deleteTransaksi(id_transaksi)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Transaksi. HTTP Status code: " +
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

    override suspend fun getTransaksibyidTransaksi(id_transaksi: Int): Transaksi {
        return transaksiApiService.getTransaksibyidTransaksi(id_transaksi).data
    }
}
package com.example.finalprojectpam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Peserta (
    val id_peserta: Int,
    val nama_peserta: String,
    val email: String,
    val nomor_telepon: String
)

@Serializable
data class AllPesertaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Peserta>
)

@Serializable
data class PesertaDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Peserta
)
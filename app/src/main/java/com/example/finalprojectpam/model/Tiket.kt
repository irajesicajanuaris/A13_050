package com.example.finalprojectpam.model

import kotlinx.serialization.Serializable

@Serializable
data class Tiket(
    val id_tiket: Int,
    val id_event: Int,
    val id_peserta: Int,
    val kapasitas_tiket: String,
    val harga_tiket: Int
)

@Serializable
data class AllTiketResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tiket>
)

@Serializable
data class TiketDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Tiket
)
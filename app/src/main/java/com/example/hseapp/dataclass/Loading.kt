package com.example.hseapp.dataclass

data class Loading(
    val `data`: List<Data>
)

data class Data(
    val attributes: Attributes,
    val id: Int
)

data class Attributes(
    val gambar1: Gambar1,
    val nama_pengawas: String,
    val tanggal: String
)

data class Gambar1(
    val `data`: DataX
)

data class DataX(
    val attributes: AttributesX,
    val id: Int
)

data class AttributesX(
    val url: String
)

package com.example.hseapp.dataclass

data class Loading(
    val `data`: List<Data>
)

data class Data(
    val attributes: Attributes,
    val id: Int
)

data class Attributes(
    val tanggal: String,
    val userId: Int,
    val image1 : String,
)
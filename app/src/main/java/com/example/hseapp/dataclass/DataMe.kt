package com.example.hseapp.dataclass

data class DataMe(
    val Nama: String,
    val ProfilePicture: ProfilePicture,
    val email: String,
    val id: Int,
    val username: String
)

data class ProfilePicture(
    val id: Int,
    val url: String
)
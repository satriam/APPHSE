package com.example.hseapp.dataclass


data class SignInBody(
    val jwt: String,
    val user: User
)

data class User(
    val id: Int
)
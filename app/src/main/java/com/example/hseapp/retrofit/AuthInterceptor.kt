package com.example.hseapp.retrofit

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context):Interceptor {
    private val sessionManager=SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("authorization","Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}
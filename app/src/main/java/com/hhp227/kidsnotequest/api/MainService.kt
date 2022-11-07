package com.hhp227.kidsnotequest.api

import com.hhp227.kidsnotequest.data.Image
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
    @GET("v2/list")
    suspend fun searchImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<Image>

    companion object {
        private val Json = Json {
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        private const val BASE_URL = "https://picsum.photos/"

        fun create(): MainService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(MainService::class.java)
        }
    }
}
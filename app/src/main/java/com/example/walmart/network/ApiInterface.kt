package com.example.walmart.network



import com.example.walmart.model.NewsResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiInterface {


    @GET("/v2/top-headlines")
    fun getCity(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Call<NewsResponse>

}
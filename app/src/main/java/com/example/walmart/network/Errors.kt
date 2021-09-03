package com.example.walmart.network


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Errors(
    @SerializedName("Code")
    val Code: String = "",
    @SerializedName("Description")
    val Description: String = ""
) : Serializable

data class ErrorResponse(
    @SerializedName("message")
    var message: String?
) : Serializable


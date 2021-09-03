package com.example.walmart.repo

import androidx.lifecycle.MutableLiveData
import com.example.walmart.model.NewsResponse
import com.example.walmart.network.Errors
import com.example.walmart.network.Output
import com.example.walmart.network.RetrofitApiService
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class NewsRepository {
    fun getNewsList(
        country: String?,
        apiKey: String?

    ): MutableLiveData<Output<NewsResponse>> {

        val data = MutableLiveData<Output<NewsResponse>>()


        data.value = Output.loading(true)
        RetrofitApiService.newsApi.getCity(country,apiKey)
            .enqueue(object : Callback<NewsResponse> {

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                    Timber.i("onFailure::callRegisterResponse = '%s'", t.message)
                    data.value = Output.loading(false)
                    val error = Errors("", t.message!!)
                    data.value = Output.failure(error)
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
//                    Timber.i("onResponse::callRegisterResponse:= '%s'", response.body())

                    data.value = Output.loading(false)
                    if (response.isSuccessful)
                        data.value = Output.success(response.body()!!)
                    else if (!response.isSuccessful && response.body() == null) {
                        val error = Errors(response.code().toString(), response.message())
                        data.value = Output.failure(error)
                    }

                }


            })

        return data
    }
}
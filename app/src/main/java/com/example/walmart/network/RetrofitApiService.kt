package com.example.walmart.network


import com.example.walmart.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitApiService {


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().client(getUnsafeOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsApi: ApiInterface = getRetrofit().create(ApiInterface::class.java)

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if(BuildConfig.DEBUG)
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(3000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/x-www-form-urlencoded")
                    /*.addHeader("Authorization", SharedPref.getSharedPrefString(Constants.TOKEN_KEY) ?: "")
                    .addHeader("x-api-key",SharedPref.getSharedPrefInt(Constants.USER_ID_KEY).toString())
                    */.build()
                chain.proceed(newRequest)
            }

        return builder.build()
    }
}
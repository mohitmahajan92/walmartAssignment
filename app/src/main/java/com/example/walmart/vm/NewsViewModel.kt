package com.example.walmart.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walmart.model.NewsResponse
import com.example.walmart.network.Output
import com.example.walmart.repo.NewsRepository

class NewsViewModel : ViewModel() {

    private val newsRepos = NewsRepository()


    fun getNewsList(
        city: String?,
        request_token: String?
    ): MutableLiveData<Output<NewsResponse>> {

        return newsRepos.getNewsList(city, request_token)
    }
}
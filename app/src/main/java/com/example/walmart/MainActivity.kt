package com.example.walmart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walmart.adapter.NewsAdapter
import com.example.walmart.databinding.ActivityMainBinding
import com.example.walmart.model.NewsResponse
import com.example.walmart.network.Output
import com.example.walmart.vm.NewsViewModel

class MainActivity : AppCompatActivity() {
    internal var activityMainBinding: ActivityMainBinding? = null
    private var newsAdapter: NewsAdapter? = null
    private var arrayList: ArrayList<NewsResponse.Article> = ArrayList()

    private val cityViewModel by viewModels<NewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        newsAdapter = NewsAdapter(arrayList)
        with(activityMainBinding) {
            this?.let {
                rvNewsList.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                rvNewsList.adapter = newsAdapter

            }
        }
        cityViewModel.getNewsList("us", "50c1bd99f6464242aab6405aa2ca35a5").observe(this, {
            updateValues(it)
        })
    }

    private fun updateValues(output: Output<NewsResponse>?) {
        when (output) {
            is Output.Progress -> {
                if (output.loading) {
                    activityMainBinding?.progressBar?.visibility = View.VISIBLE
                    activityMainBinding?.rvNewsList?.visibility = View.GONE
                } else {
                    activityMainBinding?.rvNewsList?.visibility = View.VISIBLE
                    activityMainBinding?.progressBar?.visibility = View.GONE
                }
            }
            is Output.Failure -> {
            }
            is Output.Success -> {

                if(output.status is NewsResponse && output.status.status == "ok") {

                    output.status.articles?.let {
                        updateViews(output.status)
                    }
                }else{

                }

            }
        }
    }

    private fun updateViews(newsResponse: NewsResponse) {


        newsResponse.articles.let {
            it.forEach { newsResponse ->
                arrayList.add(newsResponse)
            }

            newsAdapter?.notifyDataSetChanged()
        }
    }
    }

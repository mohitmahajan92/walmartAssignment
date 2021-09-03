package com.example.walmart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walmart.R
import com.example.walmart.WebViewActivity
import com.example.walmart.model.NewsResponse
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(
    private val arrayList: ArrayList<NewsResponse.Article>

) : RecyclerView.Adapter<NewsAdapter.HotelListViewHolder>() {

    var context: Context?=null
    fun getDayOfMonthSuffix(n: Int): String? {
//        checkArgument(n >= 1 && n <= 31, "illegal day of month: $n")
        return if (n >= 11 && n <= 13) {
            "th"
        } else when (n % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }

    class HotelListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
        val tvSource = itemView.findViewById<TextView>(R.id.tvSource)



        val imageView = itemView.findViewById<ImageView>(R.id.imageView)



        val constLayoutParent = itemView.findViewById<ConstraintLayout>(R.id.constLayoutParent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelListViewHolder {
        context= parent.context
        return HotelListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_adapter, parent, false)

        )


    }

    override fun onBindViewHolder(holder: HotelListViewHolder, position: Int) {




        holder.tvTitle.text = arrayList[position].title
        holder.tvDesc.text = arrayList[position].description
        holder.tvSource.text = "${"Source "}${arrayList[position].author}"
        val dateFromServer= dateConvert(arrayList[position].publishedAt)
        holder.tvTime.text =dateFromServer.toString()






        Glide.with(holder.itemView.context).load(arrayList[position].urlToImage)
            .into(holder.imageView)

        holder.constLayoutParent?.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", arrayList[position].url)
            context?.startActivity(intent)



        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    private fun findADayFromDate(dateFromServer:String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("d", Locale.ENGLISH)
        val date = inputFormat.parse(dateFromServer)
        return outputFormat.format(date)
    }

    private fun dateConvert(mdate:String) : String{
        val day = getDayOfMonthSuffix(findADayFromDate(mdate).toInt())
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("d'" + day + "' MMMM h:mm a", Locale.ENGLISH)
        val date = inputFormat.parse("2021-09-03T04:58:00Z")
        val formattedDate = outputFormat.format(date)
        return formattedDate
    }


}
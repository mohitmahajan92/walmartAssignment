package com.example.walmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.walmart.databinding.ActivityWebviewBinding

class WebViewActivity:AppCompatActivity() {

    private var activityWebviewBinding: ActivityWebviewBinding ? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWebviewBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_webview
        )

        intent.getStringExtra("url")?.let { activityWebviewBinding?.webView?.loadUrl(it) }
    }
}
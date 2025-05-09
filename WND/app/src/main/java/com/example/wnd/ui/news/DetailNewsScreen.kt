package com.example.wnd.ui.news

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun DetailNewsScreen(
    modifier: Modifier = Modifier,
    url: String
){
    AndroidView(
        modifier = modifier,
        factory = {
            context->
            WebView(context).apply{
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        }
    )
}

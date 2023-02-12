package com.igor.jetpackcompose.download_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color

class DownloadActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mDownloader = DownloaderImp(this)
        mDownloader.downloadFile("https://pl-coding.com/wp-content/uploads/2022/04/pic-squared.jpg")

        setContent {
            MaterialTheme {
                Text(text = "Test", color = Color.Black)
            }
        }
    }


}
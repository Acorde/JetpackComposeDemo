package com.igor.jetpackcompose.download_manager

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DownloadCompletedReceiver : BroadcastReceiver() {

    private lateinit var mDownloadManager: DownloadManager

    override fun onReceive(context: Context?, intent: Intent?) {
        mDownloadManager = context?.getSystemService(DownloadManager::class.java)!!
        if(intent?.action == "android.intent.action.DOWNLOAD_COMPLETE"){
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            val query = DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL)
            if(id != -1L && query != null){
                println("Download with ID $id finished")
            }
        }
    }
}
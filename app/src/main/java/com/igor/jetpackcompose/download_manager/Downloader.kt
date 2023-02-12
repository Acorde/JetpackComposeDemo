package com.igor.jetpackcompose.download_manager

interface Downloader {
    fun downloadFile(url : String) : Long
}
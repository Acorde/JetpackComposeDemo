package com.igor.jetpackcompose.view_pager.download

import android.content.Context
import android.print.PdfConverter
import android.util.Log
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DownloadFile(
    private val mCallback: IExecutable<FileType>,
    private val mOriginFileUrl: String,
    private val mDestinationFilePath: String,
    private val mContext: Context,
    private val fileName : String
) {

    companion object {
        private const val TAG = "DownloadFile"
        fun getType(type: String?): FileType =
            type?.let {
                when {
                    type.contains("pdf") -> return FileType.PDF
                    isImage(it) -> return FileType.IMAGE
                    type.contains("text/html") -> return FileType.TEXT_HTML
                    else -> FileType.UNKNOWN
                }
            } ?: FileType.UNKNOWN

        private fun isImage(type: String): Boolean {
            return type.contains("image") ||
                    type.contains("png") ||
                    type.contains("jpg") ||
                    type.contains("jpeg")
        }

        val token =
            "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjZCN0FDQzUyMDMwNUJGREI0RjcyNTJEQUVCMjE3N0NDMDkxRkFBRTFSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6ImEzck1VZ01Gdjl0UGNsTGE2eUYzekFrZnF1RSJ9.eyJuYmYiOjE2NzU2ODQwOTksImV4cCI6MTY3NTY4NzY5OSwiaXNzIjoiaHR0cHM6Ly9sb2dpbnEubWV1aGVkZXQuY28uaWwiLCJhdWQiOiJodHRwczovL2xvZ2lucS5tZXVoZWRldC5jby5pbC9yZXNvdXJjZXMiLCJjbGllbnRfaWQiOiJjbGllbnQuYW5kcm9pZDIiLCJzdWIiOiI1N2FkNGFlNzQ1Y2M0OTVlYTk1ZGZiZjYyMTM0NGIwMTY5YzJiYTI4NTczYTQ0MDc4YjI3ZmFiNTZmYmUwNmQ3ODBkNzlkZGM0MWUwNDQwODgzOWVjNzUzNzc1OTQ0YTgiLCJhdXRoX3RpbWUiOjE2NzU2ODQwOTksImlkcCI6Imlkc3J2IiwianRpIjoiRkJGMTA2REQ2M0JEQTQ3ODU3RTgxRTAwRkU0QTQzRTQiLCJpYXQiOjE2NzU2ODQwOTksInNjb3BlIjpbImVtYWlsIiwibWV1aGVkZXQiLCJvcGVuaWQiLCJwaG9uZSIsInByb2ZpbGUiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsiZXh0ZXJuYWwiXX0.iUrPLBE_X6pKpWQVINhAoKKt7W6y-bLsgY25E_vBEx3veMSKrQde8YEteaG7UcaWR9P1MjdID2HIYYvT6PLPYOHYX96zMvzPDF1bj_FdkiT2xE7VvkfExOPbSZ7ZnCb9n_JSy0LyPmtpt6kViq_od8F0OZCFxgM7JWKDn3XGCmBofeMPH7fP5qw6PksYdpr1gqUSFABn4t2_fcsfaBz7FvGJEf7DRMb3qAFP-Cm9x1n6C0CNzs4KlaiNVj-j_hJ-rLkUhbGdL9-9eV1PxgRoM9ICNf7vU3IRVr-wZFtw9_sDNZvndcRka5XQomyKVr_dYLbJzOrwxKB3ewdVXWGnCw"
    }

    fun doInBackground() {

        CoroutineScope(Dispatchers.IO).launch {

            deleteFileIfExist()

            try {
                val request = Request.Builder()
                    .url(mOriginFileUrl)
                    .addHeader("Authorization", token)
                  //  .addHeader("x-uid", "360f760e-1ce2-4d2a-8143-3807d9e00a25")
                    .addHeader("x-uid", "")
                    .build()

                val response = OkHttpClient().newCall(request).execute()

                response.let { mResponse ->
                    val contentType = mResponse.header("Content-Type")
                    val fileType = getType(contentType)
                    when (fileType) {
                        FileType.PDF -> {
                            createFile(mResponse, fileType, "$fileName${fileType.getEndPath()}")
                        }
                        FileType.IMAGE -> {
                            createFile(mResponse, fileType, "$fileName${fileType.getEndPath()}")
                        }
                        FileType.TEXT_HTML -> mResponse.body()?.let { body ->
                            convertHtmlToPdf(body.string(), contentType)
                        }

                        else -> {
                            executeCallback(FileType.UNKNOWN)
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                executeCallback(FileType.UNKNOWN)
            }
        }
    }

    private fun deleteFileIfExist() {
        val filePdf = File(mDestinationFilePath + "$fileName${FileType.PDF.getEndPath()}")
        if (filePdf.exists()) {
            filePdf.delete()
        }

        val fileImage = File(mDestinationFilePath + "$fileName${FileType.IMAGE.getEndPath()}")
        if (fileImage.exists()) {
            fileImage.delete()
        }
    }

    private suspend fun createFile(
        response: Response,
        fileType: FileType,
        endPath: String
    ) {


        val inputStream = response.body()!!.byteStream()
        try {
            val file = File(mDestinationFilePath, endPath)
            file.createNewFile()

            val input = BufferedInputStream(inputStream)
            val output: OutputStream = FileOutputStream(file)
            val data = ByteArray(2048)
            var count = 0
            while (input.read(data).also { count = it } != -1) {
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()
            executeCallback(fileType)

        } catch (ex: Exception) {
            ex.printStackTrace()
            executeCallback(FileType.UNKNOWN)
        }
    }

    private suspend fun executeCallback(fileType: FileType) {
        withContext(Dispatchers.Main) {
            mCallback.execute(fileType)
        }
    }

    private fun convertHtmlToPdf(htmlString: String, fileType: String?) {
        val converter = PdfConverter.getInstance()
        val file = File(mDestinationFilePath + FileType.TEXT_HTML.getEndPath())
        converter.convert(
            mContext,
            htmlString,
            file,
            mCallback,
            fileType
        )
    }

    interface IFileType {
        fun getType(): FileType
        fun getEndPath(): String?
    }

    enum class FileType : IFileType {
        PDF {
            override fun getType(): FileType = this
            override fun getEndPath(): String = ".pdf"
        },
        TEXT_HTML {
            override fun getType(): FileType = this
            override fun getEndPath(): String = ".pdf"
        },
        IMAGE {
            override fun getType(): FileType = this
            override fun getEndPath(): String = ".png"
        },
        UNKNOWN {
            override fun getType(): FileType = this
            override fun getEndPath(): String? = null
        },
        NONE {
            override fun getType(): FileType = this
            override fun getEndPath(): String? = null
        };
    }
}
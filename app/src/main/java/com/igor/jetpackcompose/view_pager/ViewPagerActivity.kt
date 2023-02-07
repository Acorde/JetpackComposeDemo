package com.igor.jetpackcompose.view_pager

import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme
import com.igor.jetpackcompose.view_pager.download.DownloadFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

class ViewPagerActivity : ComponentActivity() {

    private var mLocalFilePath : String? = null
    private val displayedFiles = mutableStateListOf<DisplayedFile?>()
    private val mFile = mutableStateOf<DisplayedFile?>(null)
    private val mViewPagerData = mutableStateOf<ViewPagerData?>(null)


//    val listOfPictures = listOf(
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7228776",
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7228777",
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7228777",
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7228776",
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7228776",
//    )

    val listOfPictures = listOf(
        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7241792",
        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7241798",
        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7241743",
    )

//    val listOfPictures = listOf(
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7241743",
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7241743",
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7241743",
//        "https://mobiq.meuhedet.co.il/umbraco/api/OnlineCommunicationApi/ReferenceFile?formSourceSystem=2&documentKey=7241743"
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLocalFilePath = filesDir.absolutePath
        setContent {
            JetpackComposeTheme {
               // val mCurrentFile = mFile
                val mCurrentData = mViewPagerData

                Surface(color = MaterialTheme.colors.surface) {
                    ViewPagerSliderWithData(initialPage = 0, data = mCurrentData.value, filesUrl = listOfPictures) {
                        selectedFileChanged(it)
                    }
                }
            }
        }
    }


    private fun selectedFileChanged(fileUrl: String) {
        if (displayedFiles.isEmpty()) {
            loadFile(fileUrl)
        } else {
            if (displayedFiles.any { it!!.mFileUrl == fileUrl }) {
                if(mViewPagerData.value is ViewPagerData.Response){
                    mViewPagerData.value = null

                }
                mViewPagerData.value = ViewPagerData.Loading
                CoroutineScope(Dispatchers.Default).launch {
                   // mFile.value = null
                   // delay(5000)
                    val displayedFile = displayedFiles.first { it!!.mFileUrl == fileUrl }
                    mViewPagerData.value = ViewPagerData.Response(displayedFile!!)
                }

            } else {
                loadFile(fileUrl)
            }
        }
    }

    private fun loadFile(fileUrl: String) {
        if(mViewPagerData.value is ViewPagerData.Response){
            mViewPagerData.value = null
        }
        mViewPagerData.value = ViewPagerData.Loading
        val aFileName = Calendar.getInstance().timeInMillis
        DownloadFile({
            val path = mLocalFilePath + "/$aFileName${it.getEndPath()}"
            val aFile = File(path)
            val displayedFile = DisplayedFile(fileType = it, mFile = aFile, fileUrl)
            mViewPagerData.value = ViewPagerData.Response(displayedFile)
            displayedFiles.add((mViewPagerData.value as ViewPagerData.Response).data)
        }, fileUrl, mLocalFilePath!!, this, fileName = "/$aFileName").doInBackground()
    }


    //    fun loadPageComplete(fileType: DownloadFile.FileType) {
//        mFileType = fileType
//        try {
//            hideProgressBar()
//            when (fileType) {
//                PDF, TEXT_HTML -> openRenderer()
//                IMAGE -> showImage()
//                UNKNOWN -> handleError()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            handleError()
//        }
//    }
    fun createFile(path: String): File {
        val mFile = File(path)
        val asset: InputStream = FileInputStream(path)
        val output: FileOutputStream = FileOutputStream(mFile)
        val buffer = ByteArray(2048)
        var size: Int
        while (asset.read(buffer).also { size = it } != -1) {
            output.write(buffer, 0, size)
        }
        val doc = PdfDocument()
        doc.writeTo(output)
        asset.close()
        output.close()
        doc.close()
        return mFile
    }
}

sealed class ViewPagerData{
    data class Response(val data : DisplayedFile) : ViewPagerData()
    object Loading : ViewPagerData()
    object Error : ViewPagerData()
}
data class DisplayedFile(val fileType: DownloadFile.FileType, val mFile: File, val mFileUrl: String)
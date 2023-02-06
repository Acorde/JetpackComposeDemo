package com.igor.jetpackcompose.view_pager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnErrorListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.google.accompanist.pager.*
import com.igor.jetpackcompose.view_pager.download.DownloadFile
import kotlinx.coroutines.delay
import java.io.File
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewPagerSliderWithData(
    initialPage: Int,
    data: ViewPagerData?,
    filesUrl: List<String>,
    selectedPage: (String) -> Unit
) {




    val pagerState = rememberPagerState(
        pageCount = filesUrl.size,
        initialPage = initialPage
    )
    val currentPage by remember { mutableStateOf(initialPage) }

    LaunchedEffect(key1 = pagerState.currentPage, block = {
        selectedPage(filesUrl[pagerState.currentPage])
    })


//    LaunchedEffect(key1 = Unit, block = {
//        while (true) {
//            yield()
//            delay(2000)
//            pagerState.animateScrollToPage(
//                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
//                animationSpec = tween(600)
//            )
//        }
//    })

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier//.verticalScroll(scrollState)
                .height(50.dp)
                .fillMaxWidth()
                .background(Color(0xFF2382CE)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ViewPager Slide",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        HorizontalPager(
            state = pagerState,
            dragEnabled = data is ViewPagerData.Response,
            modifier = Modifier
                .weight(1f)
                .padding(start = 0.dp, top = 40.dp, end = 0.dp, bottom = 40.dp)
        ) { page ->
            Card(modifier = Modifier
                .graphicsLayer {

                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(
                            minimumValue = 0f,
                            maximumValue = 1f
                        )
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(
                            minimumValue = 0f,
                            maximumValue = 1f
                        )
                    )


                }
                .fillMaxWidth()
                .padding(start = 25.dp, top = 0.dp, end = 25.dp, bottom = 0.dp),
                shape = RoundedCornerShape(20.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .align(Alignment.Center)
                ) {

                    if (data is ViewPagerData.Response) {
                        when (data.data.fileType) {
                            DownloadFile.FileType.PDF -> PdfViewer(file = data.data)
                            DownloadFile.FileType.IMAGE -> ImageViewViewer(file = data.data)
                        }
                    }

                }
            }

        }

        //Horizontal dots indicator

        HorizontalPagerIndicator(
            pagerState = pagerState, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)

        )

    }

}

@Composable
fun PdfViewer(file: DisplayedFile?) {

    file?.let {
        val scrollState = rememberScrollState()

        var mPage by remember { mutableStateOf<String?>("null") }
        var mPageCount by remember { mutableStateOf<String?>("null") }


        var animateState by remember { mutableStateOf(true) }

        LaunchedEffect(mPage, block = {
            delay(1500)
            animateState = false
        })




        Box(modifier = Modifier.fillMaxSize()) {

            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { PDFView(it, null) }) { pdfView ->

                pdfView.fromFile(file.mFile)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true)
                    .spacing(0)
                    .onPageChange { page, pageCount ->
                        mPage = page.plus(1).toString()
                        mPageCount = pageCount.toString()
                        animateState = true
                    }
                    .onError {
                        //TODO("Not yet implemented")
                    }
                    .load()
            }

            AnimatedVisibility(
                visible = animateState,
                exit = fadeOut(tween(2500))

            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.LightGray)
                        .clip(
                            CircleShape
                        )
                        .padding(5.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.TopStart),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        text = "$mPage / $mPageCount"
                    )
                }

            }

        }
    }

}

@Composable
fun ImageViewViewer(file: DisplayedFile?) {
    file?.let {


        var imgBitmap =
            BitmapFactory.decodeFile(file.mFile.absolutePath)
        imgBitmap?.let {
            Image(
                // on the below line we are specifying the drawable image for our image.
                // painter = painterResource(id = courseList[it].languageImg),

                painter = BitmapPainter(imgBitmap.asImageBitmap()),

                // on the below line we are specifying
                // content description for our image
                contentDescription = "Image",

                // on the below line we are setting the height
                // and width for our image.
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewViewPagerWithData() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ViewPager()
        }
    }
}
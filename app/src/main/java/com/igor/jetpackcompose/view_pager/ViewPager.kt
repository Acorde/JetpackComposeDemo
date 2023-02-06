package com.igor.jetpackcompose.view_pager

import android.graphics.PorterDuff
import android.widget.RatingBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.pager.*
import com.igor.jetpackcompose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@Composable
fun ViewPager() {
    MaterialTheme {
        Surface(color = MaterialTheme.colors.background) {
            ViewPagerSlider()
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewPagerSlider() {

    val pagerState = rememberPagerState(
        pageCount = dataList.size,
        initialPage = dataList.size - 1
    )

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
            modifier = Modifier
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

                val newData = dataList[page]
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .align(Alignment.Center)
                ) {

                    Image(
                        painter = painterResource(
                            id = newData.imageUri
                        ),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

//                    Column(
//                        modifier = Modifier
//                            .align(Alignment.BottomStart)
//                            .padding(15.dp)
//                    ) {
//                        Text(
//                            text = newData.title!!,
//                            style = MaterialTheme.typography.h5,
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold
//                        )
//                        val ratingBar = RatingBar(
//                            LocalContext.current, null, R.attr.ratingBarStyleSmall
//                        ).apply {
//                            rating = newData.reting!!
//                            progressDrawable.setColorFilter(
//                                android.graphics.Color.parseColor("#FF0000"),
//                                PorterDuff.Mode.SRC_ATOP
//                            )
//                        }
//                        AndroidView(
//                            factory = { ratingBar },
//                            modifier = Modifier.padding(
//                                start = 0.dp,
//                                top = 8.dp,
//                                end = 0.dp,
//                                bottom = 0.dp
//                            )
//
//                        )
//                        Text(
//                            text = newData.desc!!,
//                            style = MaterialTheme.typography.body1,
//                            color = Color.White,
//                            fontWeight = FontWeight.Normal,
//                            modifier = Modifier.padding(
//                                start = 0.dp,
//                                top = 8.dp,
//                                end = 0.dp,
//                                bottom = 0.dp
//                            )
//                        )
//
//                    }

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

@Preview(showBackground = true)
@Composable
fun PreviewViewPager() {
    MaterialTheme {
        ViewPager()
    }
}
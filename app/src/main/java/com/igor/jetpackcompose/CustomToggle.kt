package com.igor.jetpackcompose

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomToggle(modifier: Modifier = Modifier, toggleWidth: Dp = 80.dp, toggleHeight: Dp = 40.dp) {


    Box(
        modifier = modifier
            .padding(5.dp)
            //.fillMaxWidth()
            .height(toggleHeight)
            .width(toggleWidth)
            .clip(CircleShape)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0xFFCCCCCC),
                        Color(0xFFCCCCCC)
                    )
                )
            )
            .padding(top = 1.dp, end = 1.dp)
            .clip(CircleShape)
            .background(Color.White)
            .background(Color(0x56E5E5E5))
            .padding(top = 1.dp, bottom = 1.dp, start = 1.dp, end = 1.dp),
    ) {


        Box() {
            var isChecked by remember { mutableStateOf(false) }


//            LaunchedEffect(key1 = true, block = {
//                if (isChecked) {
//                    animate(
//                        initialValue = 0f,
//                        targetValue = 0f,
//                        animationSpec = infiniteRepeatable(
//                            animation = animateFloatAsState(
//                                targetValue = Random.nextFloat(
//                                    0,
//                                    toggleHeight
//                                )
//                            )
//                        )
//
//
//                    )
//                }
//            })
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_medical_stock_upper_banner_backround),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = ""
                )
            }


            val offset = animateDpAsState(targetValue = if (isChecked) toggleHeight else 0.dp)

            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(3.dp)
                    .offset(x = offset.value, y = 0.dp)
                    .shadow(elevation = 7.dp, shape = CircleShape)
            ) {

                AnimatedContent(
                    targetState = isChecked,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Transparent),
                    content = { isCheckedIn ->
                        if (isCheckedIn.not()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clip(CircleShape)
                                    .background(brush = SunBrush())
                                    .aspectRatio(1 / 1f)
                                    .clickable {
                                        isChecked = isChecked.not()
                                    }
                            )

                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clip(CircleShape)
                                    .background(color = Color.Blue)
                                    .aspectRatio(1 / 1f)
                                    .clickable {
                                        isChecked = isChecked.not()
                                    }
                            )
                        }

                    },

                    transitionSpec = {
                        slideInVertically(
                            initialOffsetY = {
                                if (isChecked) it else -it
                            }, animationSpec = tween(durationMillis = 600)
                        ) with slideOutVertically(
                            targetOffsetY = {
                                if (isChecked) -it else it
                            }, animationSpec = tween(durationMillis = 600)
                        )
                    },


                    )

            }
            if (isChecked) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun SunBrush(): Brush {
    return Brush.radialGradient(
        listOf(
            Color(0xFFE7AD58),
            Color(0xFFE7AD58),
            Color(0xFFDF9D3D),
            Color(0xFFE78C07),
            Color(0xFFE78C07),
            Color(0xFFB16A02),

            )
    )
}

@Composable
fun TransparentBrush(): Brush {
    return Brush.sweepGradient(
        listOf(
            Color.Transparent,
            Color.Transparent
        )
    )
}


@Preview(showBackground = true)
@Composable
fun CustomTogglePreview(

) {
    MaterialTheme {
        CustomToggle(toggleHeight = 80.dp, toggleWidth = 160.dp)

//        Box(modifier = Modifier
//            .width(400.dp)
//            .height(400.dp)){
//            Gesture()
//        }

    }
}

@Composable
fun Gesture() {
    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }

    var isTap by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        // Detect a tap event and obtain its position.
                        awaitPointerEventScope {
                            var position = awaitFirstDown().position

                            if (isTap) {
                                position = Offset(0f, 0f)
                            }
                            isTap = isTap.not()

                            launch {
                                // Animate to the tap position.
                                offset.animateTo(position)
                            }
                        }
                    }
                }
            }
    ) {
        Text(
            text = "oisdhcidch",
            color = Color.Blue,
            modifier = Modifier.offset { offset.value.toIntOffset() })
//        Box(modifier = Modifier
//            .clip(CircleShape)
//            .background(Color.Blue)
//            .offset { offset.value.toIntOffset() })
    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

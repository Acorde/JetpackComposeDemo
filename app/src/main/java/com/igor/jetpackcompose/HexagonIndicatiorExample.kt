package com.igor.jetpackcompose


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun HexagonSection(
    modifier: Modifier = Modifier,
    isScanning: Boolean,
    onScanButtonClick: () -> Unit,
    color: Color,
    backgroundColor: Color
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (isScanning) {
            HexagonExample(
                isFilled = false,
                hexagonColor = color,
                backgroundColor = backgroundColor,
                modifier = Modifier
                    .fillMaxSize(),
                shouldAnimateLoadingBar = true
            )
        } else {
            HexagonExample(
                isFilled = false,
                hexagonColor = color,
                backgroundColor = backgroundColor,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        HexagonExample(
            isFilled = false,
            hexagonColor = color,
            backgroundColor = backgroundColor,
            icon = Icons.Default.Search,
            modifier = Modifier
                .fillMaxSize(0.58f),
            onClick = {
                onScanButtonClick()
            }
        )
    }
}


@Composable
fun HexagonExample(
    modifier: Modifier = Modifier,
    isFilled: Boolean,
    icon: ImageVector? = null,
    hexagonColor: Color,
    backgroundColor: Color,
    iconTint: Color = Color.White,
    onClick: (() -> Unit)? = null,
    shouldAnimateLoadingBar: Boolean = false
) {

    var clickAnimationOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }

    var animationRadius by remember {
        mutableStateOf(0f)
    }

    var animationRotation by remember {
        mutableStateOf(0f)
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        if (shouldAnimateLoadingBar) {
            animate(
                0f,
                360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        1000,
                        delayMillis = 0,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            ) { value, _ ->
                animationRotation = value * 1
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            if (onClick == null) {
                                return@detectTapGestures
                            }
                            onClick()
                            clickAnimationOffset = offset
                            coroutineScope.launch {
                                animate(
                                    0f, canvasSize.height * 2,
                                    animationSpec = tween(200)
                                ) { value, _ ->
                                    animationRadius = value
                                }
                                animationRadius = 0f
                            }
                        }
                    )
                }
        ) {
            val height = size.height
            val width = size.width
            canvasSize = Size(width, height)

            val path = Path().apply {
//                moveTo(width / 2f, 0f)
//                lineTo(width, height / 4)
//                lineTo(width, height / 4 * 3)
//                lineTo(width / 2, height)
//                lineTo(0f, height / 4 * 3)
//                lineTo(0f, height / 4)

                val squareSize = 400 // Use the smaller dimension as the size of the square
                val centerX = size.width / 2
                val centerY = size.height / 2

                val halfSize = squareSize / 2

                val left = centerX - halfSize
                val top = centerY - halfSize
                val right = centerX + halfSize
                val bottom = centerY + halfSize
                moveTo(left, top)
                lineTo(right, top)
                lineTo(right, bottom)
                lineTo(left, bottom)
                close()
            }

            if (shouldAnimateLoadingBar) {
                clipPath(path) {
                    rotate(animationRotation) {
                        drawRect(
//                            startAngle = 0f,
//                            sweepAngle = 150f,
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    backgroundColor,
                                    backgroundColor,
                                    hexagonColor.copy(0.5f),
                                    hexagonColor.copy(0.5f),
                                    hexagonColor,
                                    hexagonColor,
                                    hexagonColor
                                )
                            ),
                           // useCenter = true,
                            size = canvasSize * 0.7f
                        )
                    }
                }
            } else {
                drawPath(
                    path,
                    color = hexagonColor,
                    style = if (isFilled) Fill else Stroke(
                        width = 1.dp.toPx()
                    )
                )
            }

            clipPath(path) {
                drawCircle(
                    Color.White.copy(alpha = 0.2f),
                    radius = animationRadius + 0.1f,
                    center = clickAnimationOffset
                )
            }

        }
        if (icon != null) {
            Icon(
                icon,
                contentDescription = "hexagon_icon",
                modifier = Modifier
                    .fillMaxSize(0.5f),
                tint = iconTint
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun HexagonPreview(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .aspectRatio(6 / 7f)
            .padding(15.dp)
    ) {

        var isScanning by remember {
            mutableStateOf(false)
        }
        HexagonSection(
            isScanning = isScanning,
            onScanButtonClick = {
                isScanning = !isScanning
            },
            color = Color(0xFF79ABC2),
            backgroundColor = Color.White,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(0.70f)
                .aspectRatio(6 / 7f)
        )
//        HexagonExample(
//            isFilled = true,
//            hexagonColor = Color.Blue,
//            backgroundColor = Color.White,
//            modifier = Modifier.fillMaxSize(),
//            icon = Icons.Default.Search,
//            onClick = {
//
//            },
//            shouldAnimateLoadingBar = true
//        )
    }
}
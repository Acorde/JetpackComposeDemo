package com.igor.jetpackcompose

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
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


val brightBlueColor = Color(0xFF027cf5)


@Composable
fun Hexagon(
    modifier: Modifier = Modifier,
    isFilled: Boolean,
    icon: ImageVector? = null,
    hexagonColor: Color,
    backgroundColour: Color,
    iconTint: Color = Color.White,
    onClick: (() -> Unit)? = null,
    shouldAnimateLoadingBar: Boolean = false
) {

    var clickAnimationOffset by remember { mutableStateOf(Offset.Zero) }

    var canvasSize by remember { mutableStateOf(Size.Zero) }

    var animationRadius by remember { mutableStateOf(0f) }

    var animationRotation by remember { mutableStateOf(0f) }


    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true, block = {
        if (shouldAnimateLoadingBar) {
            animate(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1000,
                        delayMillis = 0,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )

            ) { value, _ ->
                animationRadius = value
            }
        }
    })


    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offcet ->
                            if (onClick == null) {
                                return@detectTapGestures
                            }
                            onClick()
                            clickAnimationOffset = offcet
                            coroutineScope.launch {
                                animate(
                                    initialValue = 0f,
                                    targetValue = canvasSize.height * 2,
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
                moveTo(width / 2, 0f)
                lineTo(width, height / 4)
                lineTo(width, height / 4 * 3)
                lineTo(width / 2, height)
                lineTo(0f, height / 4 * 3)
                lineTo(0f, height / 4)
                close()
            }

            if (shouldAnimateLoadingBar) {
                clipPath(path) {
                    rotate(animationRotation) {
                        drawArc(
                            startAngle = 0f,
                            sweepAngle = 150f,
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    backgroundColour,
                                    backgroundColour,
                                    hexagonColor.copy(alpha = 0.5f),
                                    hexagonColor.copy(alpha = 0.5f),
                                    hexagonColor,
                                    hexagonColor,
                                    hexagonColor

                                )
                            ),
                            useCenter = true,
                            size = canvasSize * 1.1f
                        )
                    }
                }
            } else {
                drawPath(
                    path = path,
                    color = hexagonColor,
                    style = if (isFilled) Fill else Stroke(
                        width = 1.dp.toPx()
                    )
                )
            }
            clipPath(path) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.2f),
                    radius = animationRadius + 0.1f,
                    center = clickAnimationOffset
                )
            }
        }

        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "Hexagon_icon",
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHexagon() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .aspectRatio(6 / 7f)
                .padding(15.dp)
        ) {
            Hexagon(
                isFilled = true,
                hexagonColor = Color(0xFF3F51B5),
                backgroundColour = Color.White,
                modifier = Modifier.fillMaxSize(),
                icon = Icons.Default.Security,
                onClick = {

                },
                shouldAnimateLoadingBar = true
            )
        }
    }
}
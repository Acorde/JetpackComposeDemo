package com.igor.jetpackcompose.animations_compose

import android.opengl.Visibility
import android.os.Bundle
import android.widget.Space
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class AnimationActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

            }
        }
    }
}

@Composable
fun VisibilityAnimation() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        var isVisible by remember { mutableStateOf(false) }

        Button(onClick = {
            isVisible = isVisible.not()
        }) {
            Text(text = "Text")
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            Box(modifier = Modifier.background(Color.Blue))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentExample() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        var isVisible by remember { mutableStateOf(false) }

        Button(onClick = {
            isVisible = isVisible.not()
        }) {
            Text(text = "Text")
        }

        AnimatedContent(
            targetState = isVisible,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            content = { isVisible ->
                if (isVisible) {
                    Box(modifier = Modifier.background(Color.Green))
                } else {
                    Box(modifier = Modifier.background(Color.Blue))
                }
            },
            transitionSpec = {
                //  fadeIn() with fadeOut()
                slideInHorizontally(
                    initialOffsetX = {
                        -it
                    }, animationSpec = tween(2000, delayMillis = 1900)
                ) with slideOutHorizontally(
                    targetOffsetX = {
                        -it
                    }, animationSpec = tween(2000, delayMillis = 0)
                )
            }

            )
//        AnimatedContent(
//            targetState = isVisible,
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f),
//            content = { isVisible ->
//                if (isVisible) {
//                    Box(modifier = Modifier.background(Color.Green))
//                } else {
//                    Box(modifier = Modifier.background(Color.Blue))
//                }
//            },
//            transitionSpec = {
//                //  fadeIn() with fadeOut()
//                slideInHorizontally(
//                    initialOffsetX = {
//                        if (isVisible) it else -it
//                    }, animationSpec = tween(2000, delayMillis = 0)
//                ) with slideOutHorizontally(
//                    targetOffsetX = {
//                        if (isVisible) -it else it
//                    }, animationSpec = tween(2000, delayMillis = 0)
//                )
//            },
//
//            )
    }
}

@Composable
fun InfiniteAnimation() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        var showAnimation by remember { mutableStateOf(false) }

        Button(onClick = {
            showAnimation = showAnimation.not()
        }) {
            Text(text = "Text")
        }

        val transition = rememberInfiniteTransition()
        val color by transition.animateColor(
            initialValue = Color.Blue,
            targetValue = Color.Green,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(if (showAnimation) color else Color.Blue)
        )
    }
}

/**
 * Transition animation
 * Animate multiple values
 */
@Composable
fun TransitionAnimation() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        var isRound by remember { mutableStateOf(false) }

        Button(onClick = {
            isRound = isRound.not()
        }) {
            Text(text = "Text")
        }

        val transition = updateTransition(
            targetState = isRound,
            label = null
        )

        val borderRadius by transition.animateInt(
            transitionSpec = {
                tween(
                    durationMillis = 1000,
                    delayMillis = 0,
                    easing = LinearEasing
                )
            },
            label = "",
            targetValueByState = { isRound ->
                if (isRound) 100 else 0
            }
        )

        val color by transition.animateColor(
            transitionSpec = { tween(1000) },
            label = "color",
            targetValueByState = { isRound ->
                if (isRound) Color.Green else Color.Blue
            }
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(borderRadius))
                .background(color)
        )
    }
}

@Composable
fun ShapeAnimation() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var isVisible by remember { mutableStateOf(false) }
        var isRound by remember { mutableStateOf(false) }
        val spacerSize by animateIntAsState(targetValue = if (isRound) 0 else 50)




        Button(onClick = {
            isVisible = isVisible.not()
            isRound = isRound.not()
        }) {
            Text(text = "Text")
        }

        Spacer(modifier = Modifier.height(spacerSize.dp))

        val borderRadius by animateIntAsState(
            targetValue = if (isRound) 100 else 0,
            animationSpec = tween(
                durationMillis = 600,
                delayMillis = 0,
                easing = FastOutSlowInEasing
            )

        )

        //keyframes
//        val borderRadius by animateIntAsState(
//            targetValue = if(isRound) 100 else 0,
//            animationSpec = keyframes {
//
//            }
//
//        )
        val boxSize by animateIntAsState(
            targetValue = if (isRound) 100 else 200,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        Box(
            modifier = Modifier
                .size(boxSize.dp)
                .clip(RoundedCornerShape(borderRadius))
                .background(Color.Blue)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimationActivity() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))
        //VisibilityAnimation()
        //ShapeAnimation()
        //TransitionAnimation()
        // InfiniteAnimation()
        AnimatedContentExample()
    }
}
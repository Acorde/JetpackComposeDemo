package com.igor.jetpackcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun DraggableHorizontalOrVerticalView() {
    // Creating a Column Layout
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Horizontally Draggable Modifier
        var offsetX by remember { mutableStateOf(0f) }
        Text(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX -= delta
                    }
                ),
            text = "I move Horizontally!", fontSize = 20.sp
        )

        // Adding a Space of 100dp height
        Spacer(modifier = Modifier.height(100.dp))

        // Vertically Draggable Modifier
        var offsetY by remember { mutableStateOf(0f) }
        Text(
            modifier = Modifier
                .offset { IntOffset(0, offsetY.roundToInt()) }
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        offsetY += delta
                    }
                ),
            text = "I move Vertically!", fontSize = 20.sp
        )

    }
}

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dataToDrop by mutableStateOf<Any?>(null)
}

@Composable
fun fullDraggableView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dragObject()
    }
}

@Composable
fun dragObject() {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(x = offsetX.value.roundToInt(), y = offsetY.value.roundToInt())
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX.value -= dragAmount.x
                    offsetY.value += dragAmount.y

                }
            }
            .wrapContentSize()
            .background(Color.Blue), contentAlignment = Alignment.Center
    ) {
        AppYouTubePlayer(
            videoUrl = "https://www.youtube.com/embed/o-GlJvBWXls",
            isExtractYouTubeId = true,
            onFullScreenButtonClick = { _, _, _ -> })
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDragableView() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            fullDraggableView()
        }


    }
}
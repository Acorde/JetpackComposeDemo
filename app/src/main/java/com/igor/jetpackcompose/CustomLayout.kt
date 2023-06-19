package com.igor.jetpackcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun CustomFlowRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        //contain all childs.
        // measurables = is a List of Measurable. Each measurable is a child.
        // constraints = constraints is contain information about all the layout bounce
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints)
            }

            layout(
                width = constraints.maxWidth,
                height = constraints.maxHeight
            ) {
                var xPosition = 0
                placeables.forEach { placeable ->
                    placeable.place(
                        x = xPosition,
                        y = 0

                    )
                    xPosition += placeable.width
                }

            }
        },
        content = content
    )
}

@Composable
fun CustomFlowRowLines(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        //contain all childs.
        // measurables = is a List of Measurable. Each measurable is a child.
        // constraints = constraints is contain information about all the layout bounce
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints)
            }

            //Hold list of rows witch each row holds a list of Placeable
            val groupedPlaceables = mutableListOf<List<Placeable>>()

            //Current row witch holds a list of Placeable
            var currentGroup = mutableListOf<Placeable>()

            var currentGroupWidth = 0


            placeables.forEach { placeable ->
                //If we have width to place another item
                if (currentGroupWidth + placeable.width <= constraints.maxWidth) {
                    //Add view to current Group
                    currentGroup.add(placeable)
                    //Update currentGroupWidth by adding current view width
                    currentGroupWidth += placeable.width
                } else { // If the item not fit to current row, we need to create a new row and new line
                    groupedPlaceables.add(currentGroup)
                    currentGroup = mutableListOf(placeable)
                    currentGroupWidth = placeable.width
                }

            }

            //If we still has items in currentGroup
            if (currentGroup.isNotEmpty()) {
                groupedPlaceables.add(currentGroup)
            }

            layout(
                width = constraints.maxWidth,
                height = constraints.maxHeight
            ) {

                var yPosition = 0

                groupedPlaceables.forEach { row ->

                    var xPosition = 0

                    row.forEach { placeable ->
                        placeable.place(
                            x = xPosition,
                            y = yPosition

                        )
                        xPosition += placeable.width
                    }
                    yPosition += row.maxOfOrNull { it.height } ?: 0

                }


            }
        },
        content = content
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewCustomLayout() {
    MaterialTheme {
        Column(modifier = Modifier
            .wrapContentSize()
        //    .verticalScroll(rememberScrollState())

        ) {

            CustomFlowRowLines(
                modifier = Modifier
                    .wrapContentSize()
                    //.background(Color.Blue)
//                    .verticalScroll(rememberScrollState())
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .width(Random.nextInt(50, 200).dp)
                            .height(100.dp)
                            .background(Color(Random.nextLong(0xFF000000)))
                    )
                }
            }


            Text(
                color = Color.Red,
                text = "kdjcguisdcgisdugc"
            )

        }
    }
}
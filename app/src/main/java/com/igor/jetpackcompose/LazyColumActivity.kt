package com.igor.jetpackcompose

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.igor.jetpackcompose.compose_custom_component.CustomItem
import com.igor.jetpackcompose.model.PersonRepository
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme
import java.lang.Math.*
import kotlin.math.cos
import kotlin.math.sin

class LazyColumActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val repository = PersonRepository()
                    val allData = repository.getAllData()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        allData.forEach { person ->
                            CustomItem(person = person)
                        }
                    }
//                    LazyColumn {
//                        items(items = allData) { person ->
//                            CustomItem(person = person)
//                        }
//                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun LaztColumActivityPreview() {
//    JetpackComposeTheme {
//        Surface(color = MaterialTheme.colors.background) {
//            val repository = PersonRepository()
//            val allData = repository.getAllData()
//
//            LazyColumn(
//                contentPadding = PaddingValues(all = 12.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                itemsIndexed(
//                    items = allData,
//                    key = {index, peeson ->
//                        peeson.id
//                    }
//                    ) { index, person ->
//                    Log.d("IgorTest", "Index is:$index")
//                    CustomItem(person = person)
//                }
//            }
//        }
//    }
//}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun LazyColumnActivityStickyHeaderPreview() {
    JetpackComposeTheme {
        val sections = listOf("A", "B", "C", "D", "E", "F", "G")
//        Box(modifier = Modifier.fillMaxSize()){
//            RoundedHexagonShape(7f)
//        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.55f)
                .height(200.dp)
                 .clip(HexagonShape())
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center

        ){

        }
//
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            sections.forEach { section ->
//                stickyHeader {
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(Color.Blue)
//                            .padding(top = 30.dp, start = 12.dp, end = 12.dp),
//                        text = "Section $section"
//                    )
//                }
//                val repository = PersonRepository()
//                val allData = repository.getAllData()
//
//                items(items = allData) { person ->
//                    CustomItem(person = person)
//                }
////                items(10) {
////                    Text(
////                        modifier = Modifier.padding(12.dp),
////                        text = "Item $it from the section $section"
////                    )
////                }
//            }
//        }
    }
}

@Composable
fun Hexagon() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.55f)
            .height(200.dp)
            // .clip(HexagonShape)
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center

    ){
        Text(text = "Item 0")
    }
}

@Composable
fun HexagonList(){
    LazyColumn{
        item(50) { Hexagon() }
    }
}

class HexagonShape : androidx.compose.ui.graphics.Shape{


    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val strokeWidth = 4f

        val path = Path().apply {
            val squareSize = 40 // Use the smaller dimension as the size of the square
            val centerX = size.width / 2
            val centerY = size.height / 2

            val halfSize = squareSize / 2

            val left = centerX - halfSize
            val top = centerY - halfSize
            val right = centerX + halfSize
            val bottom = centerY + halfSize
//            moveTo(left, top)
//            lineTo(right, top)
//            lineTo(right, bottom)
//            lineTo(left, bottom)
//            moveTo(20f, 00f)//Square
//            lineTo(20f, 20f)
//            lineTo(40f, 20f)
//            lineTo(40f, 0f)
            moveTo(20f, 0f)
            lineTo(40f, 0f)
            lineTo(50f, 15f)
            lineTo(40f, 30f)
           // lineTo(40f, 40f)
            lineTo(20f, 30f)
            lineTo(10f, 15f)
            lineTo(20f, 0f)
//            lineTo(10f, 10f)
//            lineTo(20f, 0f)

                // lineTo(0f, 20f)
            //lineTo(right, bottom)
           // lineTo(left, bottom)
            close()

//            val centerX = size.width / 2
//            val centerY = size.height / 2
//
//            val radius = size.minDimension / 2
//            val angleDegrees = 360f / 6 // Divide 360 degrees by 6 sides of a hexagon
//
//            for (i in 0 until 6) {
//                val angleRadians = Math.toRadians((angleDegrees * i).toDouble())
//                val x = (centerX + radius * kotlin.math.cos(angleRadians)).toFloat()
//                val y = (centerY + radius * kotlin.math.sin(angleRadians)).toFloat()
//
//                if (i == 0) {
//                    moveTo(x, y)
//                } else {
//                    lineTo(x, y)
//                }
//            }


            close()
        }

        return Outline.Generic(path)
    }

}


class RoundedHexagonShape(private val cornerRadius: Float) : androidx.compose.ui.graphics.Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            val radius = size.minDimension / 2f
            val angle = 2f * PI.toFloat() / 6f

            for (i in 0 until 6) {
                val x = size.width / 2f + radius * cos(i * angle)
                val y = size.height / 2f + radius * sin(i * angle)

                if (i == 0) {
                    moveTo(x, y)
                } else {
                    lineTo(x, y)
                }
            }
            close()
        }

        return Outline.Generic(path)
    }
}


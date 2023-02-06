package com.igor.jetpackcompose

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.igor.jetpackcompose.compose_custom_component.CustomItem
import com.igor.jetpackcompose.model.PersonRepository
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme

class LazyColumActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val repository = PersonRepository()
                    val allData = repository.getAllData()
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        allData.forEach {  person ->
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            sections.forEach { section ->
                stickyHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Blue)
                            .padding(top = 30.dp, start = 12.dp, end = 12.dp),
                        text = "Section $section"
                    )
                }
                val repository = PersonRepository()
                val allData = repository.getAllData()

                items(items = allData) { person ->
                    CustomItem(person = person)
                }
//                items(10) {
//                    Text(
//                        modifier = Modifier.padding(12.dp),
//                        text = "Item $it from the section $section"
//                    )
//                }
            }
        }
    }
}
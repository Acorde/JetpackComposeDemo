package com.igor.jetpackcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.igor.jetpackcompose.ui.ui.theme.JetpackComposeTheme

class MeuhedetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun LabsStickerTableColumn(
    firstTitle: String,
    secondTitle: String,
    firstValue: String,
    secondValue: String,
) {
    Row(modifier = Modifier
        .fillMaxWidth()) {

        Row(modifier = Modifier.weight(2f), verticalAlignment = Alignment.Top) {
            Column() {
                //Title
                Text(text = firstTitle, style = MaterialTheme.typography.h6)
                //value
                Text(text = firstValue, style = MaterialTheme.typography.subtitle2)
            }
        }

        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Top) {
            Column() {
                //Title
                Text(text = secondTitle, style = MaterialTheme.typography.h6)
                //value
                Text(text = secondValue, style = MaterialTheme.typography.subtitle2)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLabsStickerTableColumn() {
    JetpackComposeTheme() {
        Box(modifier = Modifier.fillMaxWidth()) {
            LabsStickerTableColumn("אנטיביוטיקה", "מיהול", "ESBL", ">=4")
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    JetpackComposeTheme {
        Greeting2("Android")
    }
}
package com.igor.jetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.igor.jetpackcompose.models.Message
import com.igor.jetpackcompose.ui.ui.theme.JetpackComposeTheme

class OneMessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                Column {
                    MessageCard(Message("Android", "Jetpack Compose"))
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                    MyComposableButton()
                    Spacer(modifier = Modifier.height(10.dp))
                    TextFieldWithStateExample()
                    Spacer(modifier = Modifier.height(10.dp))
                    TextFieldNoStateExample("Test") { onTextChanged ->
                        Log.d("IgorTest", "text changed to: $onTextChanged")
                    }
                }

            }
        }
    }


    @Composable
    fun MessageCard(msg: Message) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(
                        1.5.dp,
                        MaterialTheme.colors.secondary,
                        CircleShape
                    ) //Set border + border shape

            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2

                )
                // Add a vertical space between the author and message texts
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.body2
                    )
                }

            }
        }

    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun MyComposableButton() {
        var muValue by remember { mutableStateOf(false) }
        //  var muValue = false // not working
        Log.d("Recomposition", "MyComposition")

        Button(
            onClick = { muValue = !muValue },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "$muValue")
            Log.d("Recomposition", "Button Content Lambda")
        }
    }

    //Composable with state
    @Composable
    fun TextFieldWithStateExample() {
        var name by remember { mutableStateOf("") }

        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
    }

    @Composable
    fun TextFieldNoStateExample(name: String, onNameChange: (String) -> Unit) {
        TextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
    }



    @Preview(showBackground = true)
    @Composable
    fun PreviewConversation() {
        JetpackComposeTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                MessageCard(Message("Android", "Jetpack Compose"))
                Spacer(modifier = Modifier.height(4.dp))
                MyComposableButton()
                TextFieldNoStateExample("Test"){onTextChanged ->

                }
            }

        }
    }
}
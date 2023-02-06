package com.igor.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme

class ColumnActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomItem(weight = 3f, color = MaterialTheme.colors.secondary)
                        CustomItem(weight = 1f)
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnScope.CustomItem(weight: Float, color: Color = MaterialTheme.colors.primary) {
    Surface(
        modifier = Modifier
            .width(200.dp)
            .weight(weight), color = color
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ColumnActivityPreview() {
    JetpackComposeTheme {
//        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//            CustomItem(weight = 3f, color = MaterialTheme.colors.secondary)
//            CustomItem(weight = 1f)
//        }
        LoginScreen()
    }
}

@Composable
fun LoginScreen() {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorState = remember { mutableStateOf("") }

    Column {
        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") }
        )
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
        )
        Text(text = errorState.value, color = Color.Red)

        Button(
            onClick = {
                if (emailState.value == "user@example.com" && passwordState.value == "password") {
                    // Navigate to home screen
                } else {
                    errorState.value = "Invalid email or password"
                }
            }
        ) {
            Text(text = "Log In")
        }

    }
}
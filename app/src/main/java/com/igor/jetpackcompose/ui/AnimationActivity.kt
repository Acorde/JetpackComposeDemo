package com.igor.jetpackcompose.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.igor.jetpackcompose.R
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme

class AnimationActivity :  ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {

            }
        }
    }
}
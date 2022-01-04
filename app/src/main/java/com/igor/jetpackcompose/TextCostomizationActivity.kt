package com.igor.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme

class TextCostomizationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextCostomization(R.string.app_name)
                        TextCostomization2("Hello world2")
                        TextCostomizationAnnotatedString()
                        TextRepeat()
                        selectableText()
                    }

                }
            }
        }
    }
}

@Composable
fun ColumnScope.TextCostomization(textId: Int) {
    Text(
        text = stringResource(id = textId),
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colors.primary),
        color = Color.Yellow,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
fun ColumnScope.TextCostomization2(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
            .width(200.dp),
        color = Color.White,
        fontSize = MaterialTheme.typography.h5.fontSize,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun selectableText(){
    SelectionContainer {
        Column {
            Text(text = "This text is selectable")
            DisableSelection {
                Text(text = "This text is not selectable")
            }
            Text(text = "This text is selectable")

        }
    }
}

@Composable
fun ColumnScope.TextCostomizationAnnotatedString() {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(textAlign = TextAlign.Center)) {

                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("A")
                }


                append("B")
                append("C")
                append("D")
                append("E")
            }
        },
        modifier = Modifier
            .padding(20.dp)
            .width(200.dp)
    )
}

@Composable
fun ColumnScope.TextRepeat(){
    Text(text = "Hellow World ".repeat(50), maxLines = 2, overflow = TextOverflow.Ellipsis)
}

@Composable
fun SuperScriptText(normalString : String, superText : String, subString : String, normalTextSize : TextUnit = MaterialTheme.typography.subtitle1.fontSize){
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontSize = MaterialTheme.typography.overline.fontSize,
            fontWeight = FontWeight.Normal,
            baselineShift = BaselineShift.Subscript
        )){
            append(subString)
        }
        withStyle(style = SpanStyle(
            fontSize = normalTextSize
        )){
            append(normalString)
        }

        withStyle(style = SpanStyle(
            fontSize = MaterialTheme.typography.overline.fontSize,
            fontWeight = FontWeight.Normal,
            baselineShift = BaselineShift.Superscript
        )){
            append(superText)
        }

    })
}

@Preview(showBackground = true)
@Composable
fun TextCostomizationPreview() {
    JetpackComposeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextCostomization(R.string.app_name)
            Divider()
            TextCostomization2("Hello world2")
            Divider()
            TextCostomizationAnnotatedString()
            Divider()
            TextRepeat()
            Divider()
            selectableText()
            Divider()
            SuperScriptText("Normal String", "SuperText String", "SubString String")
        }

    }
}
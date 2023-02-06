package com.igor.jetpackcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
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
import androidx.compose.ui.unit.LayoutDirection
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
fun selectableText() {
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
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append("Don't have an account? ")

        }

        //Start of the pushing annotation which you want to color and make them clickable later
        pushStringAnnotation(
            tag = "SignUp",// provide tag which will then be provided when you click the text
            annotation = "SignUp"
        )
        //add text with your different color/style
        withStyle(
            style = SpanStyle(
                color = Color.Red,
            )
        ) {
            append("Sign Up")
        }
        // when pop is called it means the end of annotation with current tag
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "SignUp",// tag which you used in the buildAnnotatedString
                start = offset,
                end = offset
            )[0].let { annotation ->
                //do your stuff when it gets clicked
                Log.d("Clicked", annotation.item)
            }
        }
    )
}

@Composable
fun PartClickableText(
    initialText: String,
    initialTextStyle: SpanStyle = SpanStyle(color = Color.Gray),
    clickableText: String,
    clickableTextStyle: SpanStyle = SpanStyle(color = Color.Blue, ),
    clickableTextUrl : String,
    onClick : (String?) -> Unit
    ) {
    val annotatedText = buildAnnotatedString {
        //append your initial text

        withStyle(
            style = initialTextStyle
        ) {
            append("$initialText ")

        }


        //Start of the pushing annotation which you want to color and make them clickable later
        pushStringAnnotation(
            tag = clickableText,// provide tag which will then be provided when you click the text
            annotation = clickableTextUrl
        )
        //add text with your different color/style
        withStyle(
            style = clickableTextStyle
        ) {
            append(clickableText)
        }
        // when pop is called it means the end of annotation with current tag
        pop()
    }

    ClickableText(
        modifier = Modifier.padding(15.dp),
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = clickableText,// tag which you used in the buildAnnotatedString
                start = offset,
                end = offset
            )[0].let { annotation ->
                onClick.invoke(annotation.item)
                //do your stuff when it gets clicked
                Log.d("Clicked", annotation.item)
            }
        }
    )
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
fun ColumnScope.TextRepeat() {
    Text(text = "Hellow World ".repeat(2), maxLines = 1, overflow = TextOverflow.Ellipsis)
}

@Composable
fun SuperScriptText(
    normalString: String,
    superText: String,
    subString: String,
    normalTextSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize
) {
    Text(buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = MaterialTheme.typography.overline.fontSize,
                fontWeight = FontWeight.Normal,
                baselineShift = BaselineShift.Subscript
            )
        ) {
            append(subString)
        }
        withStyle(
            style = SpanStyle(
                fontSize = normalTextSize
            )
        ) {
            append(normalString)
        }

        withStyle(
            style = SpanStyle(
                fontSize = MaterialTheme.typography.overline.fontSize,
                fontWeight = FontWeight.Normal,
                baselineShift = BaselineShift.Superscript
            )
        ) {
            append(superText)
        }

    })
}

@Composable
fun testHebrewTest(text: String) {
    JetpackComposeTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Text(
                text = "\u200F $text",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 11.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextCostomizationPreview() {
    MaterialTheme {
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
            Divider()
            testHebrewTest("(CMV IGG <6 AU/ML = NEGATIVE ) החל מ28-02-16 שינוי ערכי ייחוס")
            Divider(modifier = Modifier.padding(25.dp))
            ButtonTest()
            Divider(modifier = Modifier.padding(25.dp))
            val buttonColor =
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFFD9B3F),
                        Color(0xFFF1681E)
                    )
                )

            Divider(modifier = Modifier.padding(25.dp))
            PartClickableText(initialText = "Normal String Normal String Normal String ", clickableText = "onClick", clickableTextUrl = "Url"){
                Log.d("IgorTest", "click url is $it")
            }
            Divider(modifier = Modifier.padding(25.dp))
            GradientButton(
                "Text", buttonColor, Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

    }
}

@Composable
fun ButtonTest() {
    JetpackComposeTheme {
        val buttonColor =
            Brush.verticalGradient(
                listOf(
                    Color(0xFFFD9B3F),
                    Color(0xFFF1681E)
                )
            )

        Button(
            onClick = {

            },
            shape = RoundedCornerShape(55),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        ) {
            Box(
                modifier = Modifier
                    .background(buttonColor)
                    .border(width = 1.dp, buttonColor, shape = RoundedCornerShape(55))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Click", color = Color(0xffffffff))
            }
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(55),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text)
        }
    }
}
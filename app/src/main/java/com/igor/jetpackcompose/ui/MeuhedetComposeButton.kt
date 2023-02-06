package com.igor.jetpackcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MeuhedetComposeButton(
    buttonState: ContinueButtonStateEnum,
    buttonText: String = "Continue",
    configurationText: String? = "GLOBAL_CONTINUE",
    textColor: Color = Color.White,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.h4,
    buttonColors: ButtonsBrushColor = ButtonsBrushColor.ORANGE_GRADIENT,
    imageId: Int? = null,
    isBoarderEnabled: Boolean = false,
    boarderWidth: Dp = 1.dp,
    boarderShape: Shape = CircleShape,
    buttonContentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    buttonShape: Shape = RoundedCornerShape(55),
    boarderColor: Color = Color(0xFF5a6d7b),
    addButtonModifierToContent: Boolean = true,
    buttonModifier: Modifier = Modifier,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp, bottom = 15.dp, start = 25.dp, end = 25.dp),
    onContinueButtonClick: () -> Unit,
) {
    Button(
        modifier = Modifier.then(if (addButtonModifierToContent) modifier else buttonModifier),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = buttonContentPadding,
        shape = buttonShape,
        onClick = {
            onContinueButtonClick.let {
                if (buttonState == ContinueButtonStateEnum.ENABLE) {
                    it.invoke()
                }
            }

        },
    ) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .background(if (buttonState == ContinueButtonStateEnum.ENABLE) getBrushColor(color = buttonColors) else getDisableColors())
                .then(
                    if (isBoarderEnabled) {
                        Modifier.border(
                            width = boarderWidth,
                            shape = boarderShape,
                            color = boarderColor
                        )
                    } else Modifier
                )
                .then(if (addButtonModifierToContent) modifier else Modifier),
            contentAlignment = Alignment.Center,
        ) {
            when {
                imageId != null -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = null
                        )
                        Text(
                            text = buttonText,
                            style = textStyle,
                            color = textColor
                        )
                    }
                }
                else -> {
                    Text(
                        text = buttonText,
                        style = textStyle,
                        color = textColor
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MeuhedetSelectableComposeButton(
    buttonState: SelectableButtonStateEnum,
    buttonText: String = "Continue",
    //iAppTexts: IAppTexts? = null,
    configurationText: String? = "GLOBAL_CONTINUE",
    textColor: Color = Color.White,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.h4,
    selectedButtonColors: ButtonsBrushColor = ButtonsBrushColor.ORANGE_GRADIENT,
    unSelectedButtonColors: ButtonsBrushColor = ButtonsBrushColor.WHITE_NO_GRADIENT,
    imageId: Int? = null,
    isBoarderEnabled: Boolean = false,
    boarderWidth: Dp = 1.dp,
    boarderShape: Shape = CircleShape,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    buttonShape : Shape = RoundedCornerShape(55),
    boarderColor: Color = Color(0xFF5a6d7b),
    paddingValues: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier
        .padding(top = 15.dp, bottom = 15.dp, start = 25.dp, end = 25.dp)
        .wrapContentSize(),
    onContinueButtonClick: () -> Unit,
) {

    val selectState  =  remember { mutableStateOf(buttonState) }
    Card(
        modifier = Modifier.padding(paddingValues).wrapContentSize(),
        backgroundColor =  Color.Transparent,
        shape = buttonShape,
        onClick = {
            onContinueButtonClick.let {
                selectState.value = when (buttonState) {
                    SelectableButtonStateEnum.SELECTED -> SelectableButtonStateEnum.UNSELECTED
                    else -> SelectableButtonStateEnum.SELECTED
                }
                    it.invoke()
            }

        },
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (selectState.value == SelectableButtonStateEnum.SELECTED) getBrushColor(
                        color = selectedButtonColors
                    ) else getBrushColor(color = unSelectedButtonColors)
                )
                .then(
                    if (isBoarderEnabled) {
                        Modifier.border(
                            width = boarderWidth,
                            shape = boarderShape,
                            color = boarderColor
                        )
                    } else Modifier
                ).then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            when {
                imageId != null -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = null
                        )
                        Text(
                            text = buttonText,
                            style = textStyle,
                            color = textColor
                        )
                    }
                }
                else -> {
                    Text(
                        text = buttonText,
                        style = textStyle,
                        color = textColor
                    )
                }
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun previewMeuhedetContinueButton() {
//    MaterialTheme {
//
//        MeuhedetComposeButton(
//            ContinueButtonStateEnum.ENABLE,
//            "Button text",
//            buttonColors = ButtonsBrushColor.ORANGE_GRADIENT,
//            buttonContentPadding = PaddingValues(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
//            addButtonModifierToContent = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 15.dp, bottom = 15.dp, start = 25.dp, end = 25.dp)
//        ) {
//
//        }
//    }
//}
@Preview(showBackground = true)
@Composable
fun previewMeuhedetContinueButton() {

    val buttons = arrayListOf<String>("One", "two", "three")

    MaterialTheme {
        val lastSelectedButton by remember { mutableStateOf(0) }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(0.5f),
                text ="Text"
            )
            LazyRow(modifier = Modifier
                .weight(4f)
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(1.dp), content = {
                itemsIndexed(buttons) { indext, item ->

                    MeuhedetSelectableComposeButton(
                        buttonState = when (indext == lastSelectedButton) {
                        true -> SelectableButtonStateEnum.SELECTED
                        else -> SelectableButtonStateEnum.UNSELECTED
                    },
                        textStyle = TextStyle(fontSize = 8.sp),
                        buttonShape = RoundedCornerShape(15),
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                        paddingValues = PaddingValues(10.dp)

                    ){

                    }




//                    MeuhedetComposeButton(
//                        buttonState = ContinueButtonStateEnum.ENABLE,
//                        buttonShape = RoundedCornerShape(6),
//                        buttonText = item,
//                        buttonColors = when (indext == lastSelectedButton) {
//                            true -> ButtonsBrushColor.BLUE_GRADIENT
//                            else -> ButtonsBrushColor.WHITE_NO_GRADIENT
//                        },
//                        buttonContentPadding = PaddingValues(
//                            top = 0.dp,
//                            bottom = 0.dp,
//                            start = 0.dp,
//                            end = 0.dp
//                        ),
//                        addButtonModifierToContent = true,
//                        textStyle = TextStyle(fontSize = 8.sp),
////                    textColor = when (indext == lastSelectedButton) {
////                        true -> Color.White
////                        else -> Color.DarkGray
////                    },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(0.dp)
//                        ,
//                        buttonModifier = Modifier
//                            .wrapContentSize()
//                            .size(10.dp)
//                            .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
//
//
//                    ) {
//                        lastSelectedButton = indext
//                    }
                }
            })
        }
    }
}

enum class ButtonsBrushColor {
    ORANGE_GRADIENT,
    BLUE_GRADIENT,
    WHITE_NO_GRADIENT,
    DARK_BLUE_NO_GRADIENT,
    DARK_ORANGE_NO_GRADIENT,
}

@Composable
fun getBrushColor(color: ButtonsBrushColor): Brush {
    return when (color) {
        ButtonsBrushColor.ORANGE_GRADIENT -> orangeBrush()
        ButtonsBrushColor.BLUE_GRADIENT -> blueBrush()
        ButtonsBrushColor.DARK_BLUE_NO_GRADIENT -> noGradientDarkBlue()
        ButtonsBrushColor.DARK_ORANGE_NO_GRADIENT -> noGradientDarkOrange()
        ButtonsBrushColor.WHITE_NO_GRADIENT -> whiteNoGradientBrush()
    }
}

@Composable
fun orangeBrush(): Brush {
    return Brush.horizontalGradient(
        listOf(
            Color(0xFFFD9B3F),
            Color(0xFFF1681E)
        )
    )
}

@Composable
fun blueBrush(): Brush {
    return Brush.horizontalGradient(
        listOf(
            Color(0xFF079BD9),
            Color(0xFF079AD9)
        )
    )
}

@Composable
fun noGradientDarkBlue(): Brush {
    return Brush.horizontalGradient(
        listOf(
            Color(0xFF1546D8),
            Color(0xFF1546D8)
        )
    )
}

@Composable
fun noGradientDarkOrange(): Brush {
    return Brush.horizontalGradient(
        listOf(
            Color(0xFFFD9B3F),
            Color(0xFFFD9B3F)
        )
    )
}

@Composable
fun whiteNoGradientBrush(): Brush {
    return Brush.horizontalGradient(
        listOf(
            Color.White,
            Color.White
        )
    )
}


enum class ContinueButtonStateEnum {
    ENABLE, DISABLE
}

enum class SelectableButtonStateEnum {
    SELECTED, UNSELECTED
}

@Composable
fun getDisableColors(): Brush {
    return Brush.verticalGradient(
        listOf(
            Color(0xFFCECCCC),
            Color(0xFF929292)
        )
    )
}


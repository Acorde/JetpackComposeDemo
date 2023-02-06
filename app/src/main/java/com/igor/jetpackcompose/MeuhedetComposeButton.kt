package com.igor.jetpackcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.igor.jetpackcompose.ui.ButtonsBrushColor
import com.igor.jetpackcompose.ui.getBrushColor


@Composable
fun MeuhedetComposeButton(
    buttonState: ContinueButtonStateEnum,
    buttonText: String = "Continue",
    textColor: Color = Color.White,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.h4,
    buttonColors: ButtonsBrushColor = ButtonsBrushColor.ORANGE_GRADIENT,
    imageId: Int? = null,
    isBoarderEnabled: Boolean = false,
    boarderWidth: Dp = 1.dp,
    boarderShape: Shape = CircleShape,
    buttonShape : Shape = RoundedCornerShape(55),
    boarderColor: Color = Color.Blue,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp, bottom = 15.dp, start = 25.dp, end = 25.dp),
    onContinueButtonClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
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
            modifier = Modifier
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
                .then(modifier),
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




@Composable
fun getDisableColors(): Brush {
    return Brush.verticalGradient(
        listOf(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.onBackground
        )
    )
}

enum class ContinueButtonStateEnum {
    ENABLE, DISABLE
}

enum class SelectableButtonStateEnum {
    SELECTED, UNSELECTED
}

@Preview(showBackground = true)
@Composable
fun previewMeuhedetContinueButton() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp),
                border = BorderStroke(0.5.dp, Color.Gray),
                shape = RoundedCornerShape(12),
                elevation = 7.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 25.dp, horizontal = 15.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(8f).fillMaxWidth(), horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier.wrapContentSize(),
                            style = MaterialTheme.typography.h1,
                            fontSize = 12.sp,
                            text = "apisdhcpaoidhcpasdocvhaposhvcj"
                        )

                        MeuhedetComposeButton(
                            buttonState =  ContinueButtonStateEnum.ENABLE,
                            buttonText = "תרופות חלופיות ל-",
                            buttonColors = ButtonsBrushColor.BLUE_GRADIENT,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(top = 5.dp, bottom = 5.dp, start = 0.dp, end = 0.dp)
                        ) {

                        }
                    }

                    Image(modifier = Modifier.weight(2f), painter = painterResource(id = R.drawable.ic_medical_stock_upper_banner_pillows), contentDescription = null)
                }

            }
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
                MaterialTheme.colors.primary,
                MaterialTheme.colors.onBackground
            )
        )
    }

    @Composable
    fun blueBrush(): Brush {
        return Brush.horizontalGradient(
            listOf(
                MaterialTheme.colors.primary,
                MaterialTheme.colors.onBackground
            )
        )
    }

    @Composable
    fun noGradientDarkBlue(): Brush {
        return Brush.horizontalGradient(
            listOf(
                MaterialTheme.colors.primary,
                MaterialTheme.colors.onBackground
            )
        )
    }

    @Composable
    fun noGradientDarkOrange(): Brush {
        return Brush.horizontalGradient(
            listOf(
                MaterialTheme.colors.primary,
                MaterialTheme.colors.onBackground
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

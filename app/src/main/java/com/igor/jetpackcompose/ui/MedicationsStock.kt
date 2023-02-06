package com.igor.jetpackcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.igor.jetpackcompose.R
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme

@Composable
fun MedicationsStockSearch() {

    val mDividerHeight = remember { mutableStateOf(0.0.dp) }
    val dencity = LocalDensity.current
    val rightText = "מאוחדת פארם אונליין"

    Box(
        modifier = Modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = painterResource(id = R.drawable.ic_medical_stock_upper_banner_backround),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .padding(top = 15.dp, bottom = 15.dp, start = 20.dp, end = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val typography = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(
                modifier = Modifier
                    .weight(1.1f)
                    .onGloballyPositioned {

                        mDividerHeight.value = with(dencity) {
                            it.size.height.toDp()
                        }

                    },
                style = typography.copy(color = Color(0xFF079BD9)),
                textAlign = TextAlign.End,
                text = rightText.replace(" ", "\n"),
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .width(2.dp)
                    .height(mDividerHeight.value)
                    .clip(CircleShape), thickness = 10.dp, color = Color(0xFF079BD9)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                val typographyText = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)
                Text(
                    style = typographyText,
                    color = Color.White,
                    text = "התרופות מגיעות עד אלייך!!"
                )
                val typography = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(
                    style = typography,
                    textDecoration = TextDecoration.Underline,
                    color = Color.White,
                    text = "לרכישה"
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.ic_medical_stock_upper_banner_pillows),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                val typography = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Text(
                    style = typography,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = "כל התרופות במלאי למשלוח עד הבית"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MedicationsStockSearchPreview() {
    JetpackComposeTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MedicationsStockSearch()
        }
    }
}
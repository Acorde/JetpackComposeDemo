package com.igor.jetpackcompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme

@Composable
fun MedicationsStockSearch() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(25.dp), verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier.align(Alignment.Start),text = "סינון")
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(0.dp), color = Color.Gray)

        MedicationStockFilterType.values().forEachIndexed { index, medicationStockFilterType ->
            FilterRow(text = "Text", index)

        }
    }
}

@Composable
fun FilterRow(text : String, index : Int){
    Card(elevation = if(index == 0) 7.dp else 1.dp) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = text, modifier = Modifier.padding(vertical = 25.dp))
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MedicationsStockSearchPreview() {
      JetpackComposeTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MedicationsStockSearch()
        }
    }
}

enum class MedicationStockFilterType {
    NEAR_BY,
    PHARMACY_STOCK
}
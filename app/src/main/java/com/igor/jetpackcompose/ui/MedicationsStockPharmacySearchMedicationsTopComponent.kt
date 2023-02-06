package com.igor.jetpackcompose.ui

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*


@Composable
fun MedicationsStockPharmacySearchMedicationsTopComponent(medications: List<TestLazyClass>) {
    val itemHeight = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val isExpand = remember { mutableStateOf(false) }
    val unauthorized =
        remember { mutableStateOf(medications.filter { it.mInventoryUnauthorized == true }) }
    val authorized =
        remember { mutableStateOf(medications.filter { it.mInventoryUnauthorized == false }) }

    val heightItemCounter = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        //  Text(text = "columnSize is ${columnSize.value} Item height is ${itemHeight.value}")
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.padding(start = 0.dp),
                color = Color.Blue,
                style = MaterialTheme.typography.h2,
                text = "Title"
            )
            Row(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {
                        isExpand.value = isExpand.value.not()
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.Blue,
                    text = "All Items"
                )
            }
        }
        if (unauthorized.value.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                unauthorized.value.forEach { medication ->
                    MedicationsStockPharmacySearchMedicationsRedItems(medication)
                }
            }
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .then(if (isExpand.value) Modifier.wrapContentHeight() else Modifier.height(0.0.dp))
            ) {
                authorized.value.forEach { medication ->
                    MedicationsStockPharmacySearchMedicationsBlackItems(
                        density = density,
                        medication = medication
                    ) {

                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .then(
                        if (itemHeight.value != 0.dp) {
                            if (isExpand.value) {
                                Modifier.wrapContentHeight()
                            } else {
                                Modifier.height(itemHeight.value)
                            }
                        } else {
                            Modifier
                        }
                    ),
            ) {
                authorized.value.forEachIndexed { index, medication ->
                    MedicationsStockPharmacySearchMedicationsBlackItems(
                        density = density,
                        medication = medication
                    ) {
                        if (heightItemCounter.value < 2) {
                            itemHeight.value += it
                            heightItemCounter.value++
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun MedicationsStockPharmacySearchMedicationsBlackItems(
    density: Density,
    medication: TestLazyClass,
    heightCallback: (Dp) -> Unit
) {
    Text(
        modifier = Modifier
            .wrapContentSize()
            .onGloballyPositioned {
                with(density) {
                    heightCallback(it.size.height.toDp())
                }
            }
            .padding(top = 9.dp),
        style = MaterialTheme.typography.body1,
        color = Color.Black,
        text = "\u2022 ${medication.mValue}"
    )
}

@Composable
fun MedicationsStockPharmacySearchMedicationsRedItems(medication: TestLazyClass) {
    Column() {


        Text(
            modifier = Modifier.wrapContentSize(),
            style = MaterialTheme.typography.body1,
            color = Color.Red,
            text = "\u2022 ${medication.mValue}"
        )

        Text(
            modifier = Modifier.wrapContentSize(),
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
            text = medication.mInventoryUnauthorizedMsg
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MedicationsStockPharmacySearchMedicationsTopComponentPreview() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MedicationsStockPharmacySearchMedicationsTopComponent(
                listOf(
                    TestLazyClass(
                        mId = "1",
                        mInventoryUnauthorized = true,
                        mInventoryUnauthorizedMsg = "אין לנו אפשרות לבדוק מלאי לתרופה זו, יש צורך לפנות לרופא המטפל",
                        mValue = "ACAMOL TSI LIQ  30+10 40C"
                    ),
                    TestLazyClass(
                        mId = "2",
                        mInventoryUnauthorized = true,
                        mInventoryUnauthorizedMsg = "אין לנו אפשרות לבדוק מלאי לתרופה זו, יש צורך לפנות לרופא המטפל",
                        mValue = "ACAMOL JHFUJFY LIQ  30+10 40C"
                    ),
                    TestLazyClass(
                        mId = "3",
                        mInventoryUnauthorized = true,
                        mInventoryUnauthorizedMsg = "אין לנו אפשרות לבדוק מלאי לתרופה זו, יש צורך לפנות לרופא המטפל",
                        mValue = "ACAMOL JHFUJFY LIQ  30+10 40C"
                    ),
                    TestLazyClass(
                        mId = "4",
                        mInventoryUnauthorized = false,
                        mInventoryUnauthorizedMsg = "אין לנו אפשרות לבדוק מלאי לתרופה זו, יש צורך לפנות לרופא המטפל",
                        mValue = "ACAMOL JHFUJFY LIQ  30+10 40C"
                    ),
                    TestLazyClass(
                        mId = "5",
                        mInventoryUnauthorized = false,
                        mInventoryUnauthorizedMsg = "אין לנו אפשרות לבדוק מלאי לתרופה זו, יש צורך לפנות לרופא המטפל",
                        mValue = "ACAMOL JHFUJFY LIQ  30+10 40C"
                    ),
                    TestLazyClass(
                        mId = "6",
                        mInventoryUnauthorized = false,
                        mInventoryUnauthorizedMsg = "",
                        mValue = "ACAMOL JHFUJFY LIQ  30+10 40C"
                    )

                )
            )
        }
    }
}

data class TestLazyClass(
    var mId : String,
    var mInventoryUnauthorizedMsg: String,
    val mValue: String,
    var mInventoryUnauthorized: Boolean
)
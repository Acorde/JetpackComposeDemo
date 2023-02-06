package com.igor.jetpackcompose

import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.igor.jetpackcompose.ui.TestLazyClass

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TwoLazyColumn(data: List<TestLazyClass>) {
    var selectedText by remember { mutableStateOf(TextFieldValue(text = "")) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val mContext = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize(), backgroundColor = Color.White) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            Column(modifier = Modifier.padding(15.dp)) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.90f)
                        .wrapContentHeight()
                ) {
                    Text(text = "kusdgcvsidgvoasdivglakdsnvblasdivha olidhvladkshvlasidhv")
                }
                OutlinedTextField(value = selectedText,
                    shape = RoundedCornerShape(50),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        cursorColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        Toast.makeText(mContext, "${selectedText.text}", Toast.LENGTH_SHORT).show()
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),

                    onValueChange = {
                        selectedText = it

                    },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    label = {
                        Text(
                            modifier = Modifier.wrapContentSize(),
                            text = "חפש שם תרופה / ציוד רפואי / מק״ט",
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") },
                    trailingIcon = {
                        if (selectedText.text.isNullOrEmpty().not()) {
                            Icon(Icons.Filled.Close, "contentDescription", Modifier.clickable {
                                selectedText = TextFieldValue(text = "")
                            })
                        } else null
                    })
            }
                DoubleLazyComponent(modifier = Modifier
                    .weight(1f)
                    .padding(padding), data = data)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth()
                        ,
                        onClick = {

                        }) {

                    }

                    Text(
                        modifier = Modifier.clickable {

                        },
                        text = "Text"
                    )
                }

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DoubleLazyComponent(modifier: Modifier, data: List<TestLazyClass>) {
    val selectedData = remember { mutableStateMapOf<String, TestLazyClass>() }
    val suggestedData = remember {
        mutableStateMapOf<String, TestLazyClass>().apply {
            clear()
            putAll(data.associateBy { it.mId })
        }
    }

    var isItemVisible by remember { mutableStateOf(true) }

    val localConfig = LocalConfiguration.current

    val dividerSize =
        animateDpAsState(
            targetValue = if (selectedData.isNotEmpty()) localConfig.screenWidthDp.dp else 0.dp,
            animationSpec = tween(durationMillis = 700)
        )

    LaunchedEffect(key1 = selectedData.size, block = {
        isItemVisible = true
    })

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        content = {

            items(selectedData.values.toList()) { selectedItem ->
                MedicationItemComponent(selectedItem, true) {
                    suggestedData[selectedItem.mId] = selectedItem
                    selectedData.remove(selectedItem.mId)
                }
            }
//            if (selectedData.isNotEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .padding(horizontal = 15.dp)
                            .width(dividerSize.value),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
            //   }


            itemsIndexed(suggestedData.values.toList()) { index, suggestedItem ->
                //AnimateVisibility
                //Animate spec
                AnimatedVisibility(
                    visible = isItemVisible, exit = fadeOut(animationSpec = tween(10000)) +
                            shrinkVertically(
                                animationSpec = tween(15000)
                            ),
                    enter = fadeIn(animationSpec = tween(10000)) +
                            expandVertically(
                                animationSpec = tween(15000)
                            )
                ) {
                    MedicationItemComponent(suggestedItem, false) {
                        selectedData[suggestedItem.mId] = suggestedItem
                        suggestedData.remove(suggestedItem.mId)
                        isItemVisible = isItemVisible.not()
                    }
                }
            }


        })
}

@Composable
fun MedicationItemComponent(item: TestLazyClass, isSelected: Boolean, onItemClick: () -> Unit) {
    val icon = when (isSelected) {
        true -> R.drawable.ic_medical_stock_upper_banner_pillows
        else -> R.drawable.ic_launcher_foreground
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        border = BorderStroke(0.5.dp, Color.Gray),
        shape = RoundedCornerShape(8),
        elevation = 7.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(15.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6f)
            ) {
                Text(text = item.mValue)
                Text(text = item.mInventoryUnauthorizedMsg)
            }
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
                    .clickable(onClick = onItemClick),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TwoLazyColumnPreview() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            val data = listOf(
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
            TwoLazyColumn(data)
        }
    }
}
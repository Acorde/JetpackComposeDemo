package com.igor.jetpackcompose.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MedicationSearchComponent(suggestions: List<String>) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val lastItemSelected = remember { mutableStateOf(-1) }

    val filteredSuggestions =
        remember { mutableStateListOf<String>().apply { addAll(suggestions) } }
    var selectedText by remember { mutableStateOf(TextFieldValue(text = "")) }

    val textColors = Pair(Color(0xffffffff), Color(0x56000000))

    val textColor = remember { mutableStateOf(textColors.second) }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val keyboardController = LocalSoftwareKeyboardController.current

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {


        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            OutlinedTextField(value = selectedText,
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0x56E5E5E5),
                    unfocusedBorderColor = Color(0x56E5E5E5),
                    textColor = Color(0x56000000),
                    focusedLabelColor = Color(0x56000000),
                    cursorColor = Color(0x56000000)
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),

                onValueChange = {
                    selectedText = it
                    filteredSuggestions.clear()
                    filteredSuggestions.addAll(suggestions.filter { it.contains(selectedText.text) })


                },
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onGloballyPositioned { coordinates -> //This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    },
                label = { Text("הקלד שם יישוב") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") },
                trailingIcon = {
                    Icon(Icons.Filled.Close, "contentDescription", Modifier.clickable {
                        selectedText = TextFieldValue(text = "")
                        filteredSuggestions.clear()
                        filteredSuggestions.addAll(suggestions)
                    })
                })
            LazyColumn(
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .padding(top = 10.dp),
                content = {
                    itemsIndexed(filteredSuggestions) { index, item ->
                        val lastItemSelectedColor by animateColorAsState(
                            targetValue = when (lastItemSelected.value == index) {
                                true -> Color.Black
                                else -> Color.White
                            }
                        )
                        textColor.value = when (lastItemSelected.value == index) {
                            true -> textColors.first
                            else -> textColors.second
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(lastItemSelectedColor)
                                .padding(vertical = 10.dp, horizontal = 10.dp)
                                .clickable {
                                    lastItemSelected.value = index
                                    selectedText = TextFieldValue(
                                        text = item,
                                        selection = TextRange(item.length)
                                    )
                                    keyboardController?.hide()
                                },
                            text = item,
                            color = textColor.value
                        )

                    }
                })
        }
    }
}

@Composable
fun MedicationsStockSearchNoLocationScreen() {
    val localConfiguration = LocalConfiguration.current
    val width = localConfiguration.screenWidthDp.dp

    val text = "כדי לקבל נתונים על בתי מרקחת קרובים אליי אנו זקוקים לאישור זיהוי מיקום"
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.withInPrecent(width, 50f),
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.body1

        )
        
        Text(text = "Test", fontSize = 30.sp)
        Text(text = "Test", fontSize = 30.sp)
        Text(text = "Test", fontSize = 30.sp)

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.body1

        )
        Text(
            modifier = Modifier.fillMaxWidth(0.50f),
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.body1

        )

        Text(
            modifier = Modifier.fillMaxWidth(0.25f),
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.body1

        )

    }
}

fun Modifier.withInPrecent(parent : Dp, precent : Float) = this.width((parent.times((precent / 100))))




@Preview(showBackground = true, name = "Test")
@Composable
private fun TestPreview() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//            MedicationSearchComponent(
//                listOf(
//                    "נתניה",
//                    "תל אביב",
//                    "נתנ",
//                    "נהריה",
//                    "תל מונד",
//                    "ירושליים"
//                )
//            )

            MedicationsStockSearchNoLocationScreen()
        }
    }
}
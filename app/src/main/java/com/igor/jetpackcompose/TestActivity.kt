package com.igor.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import java.text.SimpleDateFormat
import java.util.*

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //  Greeting3("Android")
                }
            }
        }
    }
}

@Composable
fun Test() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            //Title
            Column(modifier = Modifier.fillMaxWidth()) { //Risk item row
                Row(modifier = Modifier.fillMaxWidth()) {

                    Text(
                        text = "הסיכון המשולב לתסמונת דאון לפי הגיל והבדיקות",
                        modifier = Modifier.weight(7f),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body2,
                        maxLines = 2
                    )


                    Text(
                        text = "1: 7461".replace(" ", ""),
                        modifier = Modifier.weight(2f),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body2
                    )


                }
            }
        }

        //Risks

    }
}

@Composable
fun CircleCheckBox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onChecked: () -> Unit = {},
    color: Color,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onChecked() }
    ) {
        Icon(
            imageVector = if (isChecked) Icons.Default.CheckCircle else Icons.Outlined.Circle,
            contentDescription = "stringResource(R.)",
            tint = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    JetpackComposeTheme {
        CircleCheckBox(isChecked = false, color = Color(0xFF009BDF))
    }
}



fun main() {
//    var data = arrayListOf<TestClass>(TestClass("Igor", 41), TestClass("Igor", 45), TestClass("Daliel", 40), TestClass("Igor", 41))
//    CoroutineScope(TestCoroutineDispatcher()).launch {
////        val firstDate = Date(1653488725520)
////        val secondDate = Date(1653488734717)
////        //1653488725520
////        //1653488734717
////        println("The date are similar ${firstDate.time}")
////        delay(10000)
////        println("The date are similar after")
////        val secondDate = Calendar.getInstance().time
//
//        val firstDate = Date(1653488766520)
//        val secondDate = Date(1653488734717)
//
//        println("The date are similar = ${compareDateWithoutTime(firstDate, secondDate)}")
//    }


    print("נקות".to())

}

fun compareDateWithoutTime(d1: Date?, d2: Date?): Boolean? {
    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
    return sdf.format(d1).equals(sdf.format(d2))
}

fun <T> List<T>.uniqued() = when (isEmpty()) {
    true -> listOf()
    else -> zipWithNext().filter { it.first != it.second }.map { it.first } + last()
}

private fun String.to(show: Boolean = true): String {
    return if (show && this.isNotEmpty() && this.first() != 'ל') {
        "ל$this"
    } else this
}

class TestClass {
    var name: String? = null
    var age: Int? = null

    constructor(name: String?, age: Int?) {
        this.name = name
        this.age = age
    }

    companion object {
        fun optInt(b: Boolean): TestClass? {
            return if (b) TestClass(null, 32) else null
        }
    }
}

//fun TestClass.optInit(b: Boolean): TestClass? {
//    return if(b) TestClass("kug", 32) else null
//}
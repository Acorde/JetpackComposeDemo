package com.igor.jetpackcompose.achedualCalendar

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.HebrewCalendar
import android.provider.CalendarContract.Calendars
import android.util.Log
import android.util.SparseArray
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.igor.jetpackcompose.R
import com.igor.jetpackcompose.achedualCalendar.ScheduleCalendarItemState.*
import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter
import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar
import net.sourceforge.zmanim.hebrewcalendar.JewishDate
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ScheduleCalendarWrapper() {
    val mCalendar = remember { mutableStateOf(Calendar.getInstance()) }

    val mHebrewCalendar by remember { mutableStateOf(JewishCalendar()) }
    val mHebrewCalendarDateFormatter by remember {
        mutableStateOf(HebrewDateFormatter().apply {
            this.isHebrewFormat = true
        })
    }

    var mHebrewYear by remember { mutableStateOf("") }

    val mSelectedMonth = remember { mutableStateOf(mCalendar.value.get(Calendar.MONTH)) }

    val mSelectedDate = remember { mutableStateOf(mCalendar.value.get(Calendar.DAY_OF_MONTH)) }

    var monthDaysNumber by remember { mutableStateOf(mCalendar.value.getActualMaximum(Calendar.DAY_OF_MONTH)) }

    val monthArr = remember { mutableStateMapOf<String, String>() }


    var mFirstDatOfMonth by remember {
        mutableStateOf(
            getFirstDateOfMonth(
                month = mSelectedMonth.value,
                mCalendar.value.get(Calendar.YEAR)
            )
        )
    }

    LaunchedEffect(key1 = mSelectedMonth.value, block = {

        when (mSelectedMonth.value) {
            12 -> {
                val year = mCalendar.value.get(Calendar.YEAR)
                mCalendar.value.set(Calendar.YEAR, year + 1)
                mSelectedMonth.value = 0
            }
            -1 -> {
                val year = mCalendar.value.get(Calendar.YEAR)
                mCalendar.value.set(Calendar.YEAR, year - 1)
                mSelectedMonth.value = 11
            }

        }

        mCalendar.value.set(Calendar.MONTH, mSelectedMonth.value)
        monthDaysNumber = mCalendar.value.getActualMaximum(Calendar.DAY_OF_MONTH)
        mFirstDatOfMonth =
            getFirstDateOfMonth(month = mSelectedMonth.value, mCalendar.value.get(Calendar.YEAR))
        mCalendar.value.set(Calendar.DAY_OF_MONTH, 1)
        mHebrewCalendar.setDate(mCalendar.value.time)
        monthArr.clear()
        mHebrewYear = mHebrewCalendarDateFormatter.formatHebrewNumber(mHebrewCalendar.jewishYear)

    })

    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScheduleCalendarMontHeader(
                    mSelectedMonth = mSelectedMonth,
                    mCalendar = mCalendar,
                    monthArr = monthArr,
                    mHebrewYear = mHebrewYear
                )
                Spacer(modifier = Modifier.padding(30.dp))
                ScheduleCalendar(
                    month = mSelectedMonth,
                    hebrewCalendar = mHebrewCalendar,
                    hebrewFormatter = mHebrewCalendarDateFormatter,
                    selectedDate = mSelectedDate,
                    mCalendar = mCalendar,
                    monthDaysNumber = monthDaysNumber,
                    mFirstDatOfMonth = mFirstDatOfMonth,
                    monthArr = monthArr
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScheduleCalendar(
    month: MutableState<Int>,
    selectedDate: MutableState<Int>,
    mCalendar: MutableState<Calendar>,
    hebrewCalendar: JewishCalendar,
    hebrewFormatter: HebrewDateFormatter,
    monthDaysNumber: Int,
    mFirstDatOfMonth: Int,
    monthArr: MutableMap<String, String>
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

//        val mFirstDatOfMonth =
//            getFirstDateOfMonth(month = month.value, mCalendar.value.get(Calendar.YEAR))

        val mMonthDaysNumber = monthDaysNumber + (mFirstDatOfMonth - 1)


        LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 50.dp), modifier = Modifier.fillMaxWidth()
        ) {
            items(HebrewDateLetters.values().size) { itemIndex ->

                val heb: HebrewDateLetters = HebrewDateLetters.values()[itemIndex]
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .aspectRatio(1f)
                ) {
                    Text(text = heb.getHebrewLetter(), fontWeight = FontWeight.Bold)
                }


            }

            items(mMonthDaysNumber) { day ->

                if (day < mFirstDatOfMonth - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                            .aspectRatio(1f)
                    ) {
                        Text(text = " ")
                    }
                } else {
                    ScheduleCalendarItem(
                        day = (day + 1) - (mFirstDatOfMonth - 1),
                        calendar = mCalendar.value,
                        hebrewCalendar = hebrewCalendar,
                        hebrewFormatter = hebrewFormatter,
                        monthArr = monthArr,
                        mSelectedDate = selectedDate.value
                    ) { clickedDay ->
                        selectedDate.value = clickedDay
                    }
                }
            }
        }
    }


}

@Composable
fun ScheduleCalendarMontHeader(
    mSelectedMonth: MutableState<Int>,
    mCalendar: MutableState<Calendar>,
    monthArr: MutableMap<String, String>,
    mHebrewYear: String
) {

    val mContext = LocalContext.current

    var mHebrewMonthName by remember {
        mutableStateOf(
            getMonthName(
                context = mContext,
                month = mSelectedMonth.value
            ) + " ${mCalendar.value.get(Calendar.YEAR)}"
        )
    }

    LaunchedEffect(key1 = mSelectedMonth.value, block = {
        mHebrewMonthName = getMonthName(
            context = mContext,
            month = mSelectedMonth.value
        ) + " ${mCalendar.value.get(Calendar.YEAR)}"
    })

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowLeft,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    mSelectedMonth.value += 1
                }
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = mHebrewMonthName, modifier = Modifier.wrapContentWidth(), fontSize = 30.sp)
            Text(
                text = "${monthArr.entries.joinToString(separator = "-") { it.value }} $mHebrewYear",
                modifier = Modifier.wrapContentWidth(),
                fontSize = 20.sp
            )

        }
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    mSelectedMonth.value -= 1
                }
        )
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleCalendarItem(
    day: Int,
    calendar: Calendar,
    mSelectedDate: Int,
    hebrewCalendar: JewishCalendar,
    hebrewFormatter: HebrewDateFormatter,
    monthArr: MutableMap<String, String>,
    onItemClick: (Int) -> Unit
) {


    val mItemState =
        remember(mSelectedDate) { mutableStateOf(if (mSelectedDate == day) SELECTED else ENABLE) }

    var hebrewDateLetter by remember { mutableStateOf("") }

    LaunchedEffect(key1 = day, block = {
        hebrewCalendar.setDate(
            Calendar.getInstance().apply {
                this.time = calendar.apply {
                    this.set(Calendar.SECOND, 0)
                    this.set(Calendar.MINUTE, 0)
                    this.set(Calendar.HOUR, 0)
                }.time
                this.set(Calendar.DAY_OF_MONTH, day)
            }
        )

        hebrewDateLetter =
            getHebrewDay(
                hebrewFormatter.formatHebrewNumber(
                    hebrewCalendar.jewishDayOfMonth
                ), hebrewFormatter.formatMonth(hebrewCalendar)
            ).toString()


        val mHebrewMonth = hebrewFormatter.formatMonth(hebrewCalendar)
        monthArr[mHebrewMonth] = mHebrewMonth
        Log.d("IgorMap", "${monthArr.values.toList()}")
    })









    Log.d(
        "hebrewDateLetter",
        " ${SimpleDateFormat("dd/MM/yyyy").format(hebrewCalendar.time)} -  $hebrewDateLetter"
    )
    Log.d(
        "aaaaaaa",
        " ${hebrewFormatter.formatHebrewNumber(hebrewCalendar.jewishYear)}"
    )

    Card(modifier = Modifier
        .wrapContentSize(),
        backgroundColor = Color.Transparent,
        elevation = (if (mItemState.value == ScheduleCalendarItemState.SELECTED) 3.dp else 0.dp),
        shape = RoundedCornerShape(100),
        onClick = {
            onItemClick(day)
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (mItemState.value == SELECTED) blueBrush() else transparentBrush())

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = day.toString())
                Text(text = hebrewDateLetter, fontSize = 13.sp)
            }
        }
    }

}


fun getHebrewDate(date: Date) {

    val cal = HebrewCalendar()
    //cal.getDisplayName()
    cal.getDateTimeFormat(1, 1, Locale("he"))
}

@Preview(showBackground = true)
@Preview(device = "spec:width=1920dp,height=1080dp,dpi=960")
@Composable
fun PreviewCalendar() {
    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

            ScheduleCalendarWrapper()

        }
    }
}

enum class ScheduleCalendarItemState {
    SELECTED, ENABLE, DISABLE
}


enum class HebrewDateLetters {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;


}

fun HebrewDateLetters.getHebrewLetter(): String {
    return when (this) {
        HebrewDateLetters.SUNDAY -> "א׳"
        HebrewDateLetters.MONDAY -> "ב׳"
        HebrewDateLetters.TUESDAY -> "ג׳"
        HebrewDateLetters.WEDNESDAY -> "ד׳"
        HebrewDateLetters.THURSDAY -> "ה׳"
        HebrewDateLetters.FRIDAY -> "ו׳"
        HebrewDateLetters.SATURDAY -> "ש׳"
    }
}
//
//fun main() {
//    val daysOfMonth : YearMonth = YearMonth.of(2023, 5)
//    println(daysOfMonth.lengthOfMonth())
//}

@SuppressLint("SuspiciousIndentation")
fun main() {
//    CoroutineScope(Dispatchers.IO).launch {
//        val cal = HebrewCalendar.getInstance()
//        launch(Dispatchers.Main) {
//            val sd = SimpleDateFormat("EEEE")
//            // cal.getDisplayName()
//            val hDate = sd.format(cal.time)
//            // val dd = cal.getDateTimeFormat(1, 1, ULocale.Category.FORMAT)
//            println("hDate= $hDate")
//            println(".......******.......")
//        }
//
//    }


    val jd: JewishDate = JewishCalendar() // current date 23 Nissan, 5773
    val hdf = HebrewDateFormatter()
    hdf.isHebrewFormat = true
    jd.setDate(Calendar.getInstance().time) // set the date to 21 Shevat, 5729

    val hDate = getHebrewDay(
        hdf.formatHebrewNumber(jd.jewishDayOfMonth),
        hdf.formatMonth(jd)
    )

    val mCalendar = Calendar.getInstance()
    val monthDaysNumber = mCalendar.getActualMaximum(Calendar.DAY_OF_WEEK_IN_MONTH)
    val currentMonth = mCalendar.get(Calendar.MONTH)
    val dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK)
    val dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH)
    println("hDate is : $hDate")
    println("currentMonth is : $currentMonth")
    println("monthDaysNumber is : $monthDaysNumber")
    println("dayOfWeek is : $dayOfWeek")
    println("dayOfMonth is : $dayOfMonth")
    println("firstDayOfWeek is : ${getFirstDateOfMonth(1, 2023)}")

}

fun formatStringParams(vararg params: String): String? {
    var result = ""
    for (param in params) {
        if (param.isNotEmpty()) {
            result += "$param "
        }
    }
    return result
}

fun getHebrewDay(vararg params: String): String? {
    Log.d("getHebrewDay", params.toString())
    return params[0]
}

private fun getDayOfWeek(): Int {
    val mCalendar = Calendar.getInstance()
    val dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK)
    return dayOfWeek

}

private fun getFirstDateOfMonth(month: Int, year: Int): Int {
    val mCalendar = Calendar.getInstance()
    // mCalendar.set(Calendar.MONTH, if(month==0) 1 else month)
    mCalendar.set(Calendar.MONTH, month)
    mCalendar.set(Calendar.YEAR, year)
    mCalendar.set(Calendar.DAY_OF_MONTH, 1)
    Log.d("IgorTest", "getFirstDayOfWeek is = ${mCalendar.get(Calendar.DAY_OF_WEEK)}")
    return mCalendar.get(Calendar.DAY_OF_WEEK)
}


private fun getMonthDays(monthIndex: Int): Int {
    val mCalendar = Calendar.getInstance()
    mCalendar.set(Calendar.MONTH, monthIndex)
    return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}


@Composable
fun blueBrush(): Brush {
    return Brush.horizontalGradient(
        listOf(
            Color(0xFF079BD9),
            Color(0xFFF0D79C0)
        )
    )
}

@Composable
fun transparentBrush(): Brush {
    return Brush.horizontalGradient(
        listOf(
            Color(0x00000000),
            Color(0x00000000)
        )
    )
}


fun getMonthName(context: Context, month: Int?): String? {
    val monthNames = SparseArray<String?>()
    monthNames.put(0, context.getString(R.string.January))
    monthNames.put(1, context.getString(R.string.February))
    monthNames.put(2, context.getString(R.string.March))
    monthNames.put(3, context.getString(R.string.April))
    monthNames.put(4, context.getString(R.string.May))
    monthNames.put(5, context.getString(R.string.June))
    monthNames.put(6, context.getString(R.string.July))
    monthNames.put(7, context.getString(R.string.August))
    monthNames.put(8, context.getString(R.string.September))
    monthNames.put(9, context.getString(R.string.October))
    monthNames.put(10, context.getString(R.string.November))
    monthNames.put(11, context.getString(R.string.December))
    return if (monthNames[month!!] != null) {
        monthNames.valueAt(month)
    } else null
}
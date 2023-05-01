package com.igor.jetpackcompose.achedualCalendar

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.SparseArray
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.igor.jetpackcompose.R
import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter
import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar
import java.util.*
import kotlin.collections.List


@Composable
fun ScheduleCalendarWrapper() {

    val mScheduleMonth = remember { mutableStateOf(ScheduleCalendarMonth()) }
    val calendarList = mScheduleMonth.value.getScheduleCalendarDays()

    MaterialTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScheduleCalendarMontHeader(
                    mSelectedMonth = mScheduleMonth.value.getCurrentMonth(),
                    mCalendar = mScheduleMonth.value.getCalendar()!!,
                    jewishDate = mScheduleMonth.value.getJewishDate().value
                ) {
                    mScheduleMonth.value.setNewMonth(it)
                }
                Spacer(modifier = Modifier.padding(15.dp))
                ScheduleCalendar(
                    aScheduleCalendarMonth = mScheduleMonth.value,
                    mScheduleCalendarMonth = calendarList
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScheduleCalendar(
    aScheduleCalendarMonth: ScheduleCalendarMonth,
    mScheduleCalendarMonth: List<ScheduleCalendarMonth.ScheduleCalendarDay>
) {

    var mSelectedDate = aScheduleCalendarMonth.mSelectedDate

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            cells = GridCells.Adaptive(minSize = 50.dp)
        ) {
            items(HebrewDateLetters.values().size) { itemIndex ->

                val heb: HebrewDateLetters = HebrewDateLetters.values()[itemIndex]

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = heb.getHebrewLetter(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            itemsIndexed(mScheduleCalendarMonth) { index, mScheduleCalendarDay ->
                if (mScheduleCalendarDay.dayNumber == null || mScheduleCalendarDay.mHebrewName == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp)
//                            .aspectRatio(1f)
                    ) {
                        Text(text = " ")
                    }
                } else {
                    ScheduleCalendarItem(
                        day = mScheduleCalendarDay,
                    ) { clickedDay ->
                        mSelectedDate?.buttonState =
                            ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.ENABLE
                        aScheduleCalendarMonth.onDateSelected(index, clickedDay)
                        mSelectedDate = clickedDay

                    }
                }
            }
        }
    }
}


@Composable
fun ScheduleCalendarMontHeader(
    mSelectedMonth: Int,
    mCalendar: Calendar,
    jewishDate: String,
    onItemClick: (Int) -> Unit
) {

    val mContext = LocalContext.current

    val mMonth = remember(mSelectedMonth) { mutableStateOf(mSelectedMonth) }

    var mHebrewMonthName by remember(jewishDate) {
        mutableStateOf(
            getMonthName(
                context = mContext,
                month = mCalendar.get(Calendar.MONTH)
            ) + " ${mCalendar.get(Calendar.YEAR)}"
        )
    }


    LaunchedEffect(key1 = mSelectedMonth, block = {
        mHebrewMonthName = getMonthName(
            context = mContext,
            month = mSelectedMonth
        ) + " ${mCalendar.get(Calendar.YEAR)}"
    })

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .weight(0.1f)
                .scale(1.1f)
                .size(35.dp)
                .clickable {
                    mMonth.value += 1
                    onItemClick(mMonth.value)
                }
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = mHebrewMonthName,
                modifier = Modifier.wrapContentWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = jewishDate,
                modifier = Modifier.wrapContentWidth(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )

        }
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .weight(0.1f)
                .scale(1.1f)
                .size(35.dp)
                .clickable {
                    mMonth.value -= 1
                    onItemClick(mMonth.value)
                }
        )
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleCalendarItem(
    day: ScheduleCalendarMonth.ScheduleCalendarDay,
    onItemClick: (ScheduleCalendarMonth.ScheduleCalendarDay) -> Unit
) {

    val textColor by rememberUpdatedState(
        newValue = when (day.buttonState) {
            ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.SELECTED -> Color.White
            ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.ENABLE -> Color.Black
            ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.DISABLE -> Color.LightGray.copy(0.5f)
        }
    )

    Card(modifier = Modifier
        .fillMaxSize(),
        backgroundColor = Color.Transparent,
        elevation = (if (day.buttonState == ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.SELECTED) 3.dp else 0.dp),
        shape = RoundedCornerShape(100),
        enabled = day.buttonState != ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.DISABLE,
        onClick = {

            if (day.buttonState == ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.ENABLE) {
                day.buttonState =
                    ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.SELECTED
                onItemClick(day)
            }
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .background(if (day.buttonState == ScheduleCalendarMonth.ScheduleCalendarDay.ScheduleCalendarItemState.SELECTED) blueBrush() else transparentBrush()),
            contentAlignment = Alignment.Center


        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = day.dayNumber.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 19.dp.textDp(),
                    color = textColor

                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = day.mHebrewName!!,
                    textAlign = TextAlign.Center,
                    fontSize = 14.dp.textDp(),
                    color = textColor
                )
            }
        }
    }

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

class ScheduleCalendarMonth {

    private var mCalendar: Calendar? = null

    private var mJewishCalendar: JewishCalendar? = null
    private var mHebrewCalendarDateFormatter: HebrewDateFormatter? = null

    private var georgianDate = mutableStateOf("")
    private var jewishDate = mutableStateOf("")

    private var mDays = mutableStateListOf<ScheduleCalendarDay>()
    private var mJewishMonthArr: MutableMap<String, String> = mutableMapOf()
    private var mJewishYearArr: MutableMap<String, String> = mutableMapOf()
    var mSelectedDate by mutableStateOf<ScheduleCalendarDay?>(null)
        private set

    init {
        mCalendar = Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MINUTE, 0)
            this.set(Calendar.HOUR, 0)
        }
        mJewishCalendar = JewishCalendar()
        mHebrewCalendarDateFormatter = HebrewDateFormatter().apply { this.isHebrewFormat = true }
        setNewMonth(mCalendar!!.get(Calendar.MONTH))
    }


    fun setNewMonth(month: Int) {
        mCalendar!!.set(Calendar.DAY_OF_MONTH, 1)
        mCalendar!!.set(Calendar.MONTH, handleMonth(month))
        mJewishCalendar?.setDate(mCalendar!!.time)
        mDays.clear()
        mJewishMonthArr.clear()
        mJewishYearArr.clear()
        setDays()
        setGeorgianDate()
        setJewishDate()
    }

    private fun handleMonth(month: Int): Int {
        return when (month) {
            12 -> {
                val year = mCalendar!!.get(Calendar.YEAR)
                mCalendar!!.set(Calendar.YEAR, year + 1)
                0
            }
            -1 -> {
                val year = mCalendar!!.get(Calendar.YEAR)
                mCalendar!!.set(Calendar.YEAR, year - 1)
                11
            }
            else -> {
                month
            }

        }
    }

    private fun setJewishDate() {
        jewishDate.value = ""
        jewishDate.value = getJewishYearName()
    }

    fun getCurrentMonth(): Int {
        return mCalendar!!.get(Calendar.MONTH)
    }

    private fun setGeorgianDate() {
        georgianDate.value = ""
        georgianDate.value = getJewishYearName()
    }

    fun getJewishDate(): MutableState<String> {
        return jewishDate
    }


    private fun setDays() {
        val dayInMonth = mCalendar!!.getActualMaximum(Calendar.DAY_OF_MONTH)
        val mFirstDatOfMonth =
            getFirstDateOfMonth(mCalendar!!.get(Calendar.MONTH), mCalendar!!.get(Calendar.YEAR))


        for (i in 1..(dayInMonth + (mFirstDatOfMonth - 1))) {
            mDays.add(
                when {
                    i < mFirstDatOfMonth -> ScheduleCalendarDay(null, null)
                    else -> {
                        mCalendar!!.set(Calendar.DAY_OF_MONTH, i - (mFirstDatOfMonth - 1))
                        mJewishCalendar!!.setDate(mCalendar)

                        val mHebrewMonth =
                            mHebrewCalendarDateFormatter!!.formatMonth(mJewishCalendar)

                        mJewishMonthArr[mHebrewMonth] = mHebrewMonth
                        mJewishYearArr[getJewishYear()] = getJewishYear()
                        ScheduleCalendarDay(
                            dayNumber = i - (mFirstDatOfMonth - 1),
                            mHebrewName = mHebrewCalendarDateFormatter?.formatHebrewNumber(
                                mJewishCalendar!!.jewishDayOfMonth
                            ),
                            buttonState = if (i < 20) ScheduleCalendarDay.ScheduleCalendarItemState.DISABLE else ScheduleCalendarDay.ScheduleCalendarItemState.ENABLE
                        )

                    }
                }
            )
        }

    }

    fun getScheduleCalendarDays(): List<ScheduleCalendarDay> {
        return mDays
    }

    fun getCalendar(): Calendar? {
        return mCalendar
    }

    private fun getHebrewMonths(): String {
        return mJewishMonthArr.entries.joinToString(separator = "-") { it.value }
    }

    private fun getJewishYear(): String {
        return mHebrewCalendarDateFormatter!!.formatHebrewNumber(mJewishCalendar!!.jewishYear)
    }

    private fun getJewishYearName(): String {

        return when (mJewishYearArr.size > 1) {
            true -> {
                var text = ""
                mJewishYearArr.keys.forEachIndexed { index, c ->
                    text += "${mJewishMonthArr.keys.toList()[index]} $c ${if (index < mJewishYearArr.toList().lastIndex) " - " else ""}"
                }
                text
            }
            else -> {
                "${getHebrewMonths()} ${getJewishYear()}"
            }
        }
    }

    private fun getFirstDateOfMonth(month: Int, year: Int): Int {
        val mCalendar = Calendar.getInstance()
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.DAY_OF_MONTH, 1)
        return mCalendar.get(Calendar.DAY_OF_WEEK)
    }

    fun onDateSelected(index: Int, selectedDay: ScheduleCalendarDay) {
        mDays.updateItemInIndex(index, selectedDay)

        mDays.forEach {
            Log.d("IgorTest", "${it.buttonState}")
        }
    }


    data class ScheduleCalendarDay(
        val dayNumber: Int?,
        val mHebrewName: String?,
        var buttonState: ScheduleCalendarItemState = ScheduleCalendarItemState.DISABLE
    ) {
        enum class ScheduleCalendarItemState {
            SELECTED, ENABLE, DISABLE
        }
    }

}

fun <T> MutableList<T>.updateItemInIndex(index: Int, element: T) {
    removeAt(index)
    add(index, element)
}

@Composable
fun Dp.textDp(): TextUnit = with(LocalDensity.current) {
    this@textDp.toSp()
}
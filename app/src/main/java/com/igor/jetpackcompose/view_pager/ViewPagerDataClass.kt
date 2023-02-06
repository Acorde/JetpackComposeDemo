package com.igor.jetpackcompose.view_pager

import com.igor.jetpackcompose.R

data class ViewPagerDataClass(

    val title : String?,
    val reting : Float?,
    val desc : String?,
    val imageUri : Int

)

val dataList = listOf(
    ViewPagerDataClass(title = "one", reting = 4.0f, desc = "description", R.drawable.hybrid_popup),
    ViewPagerDataClass(title = "two", reting = 5.0f, desc = "description", R.drawable.adb_video),
    ViewPagerDataClass(title = "three", reting = 5.0f, desc = "description", R.drawable.landing_popup),
    ViewPagerDataClass(title = "four", reting = 5.0f, desc = "description", R.drawable.landing_popup_kosher)
)
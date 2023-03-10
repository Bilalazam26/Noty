package com.portfolio.noty.utils

import android.content.Context
import android.widget.Toast
import java.util.*

fun makeToast(context: Context?, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun getTodayDate(): String{
    val calendar: Calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$day ${setupMonth(month)},$year"
}

private fun setupMonth(month: Int): String =
    when(month){
        0->"Jan"
        1->"Feb"
        2->"Mar"
        3->"Apr"
        4->"May"
        5->"Jun"
        6->"Jul"
        7->"Aug"
        8->"Sep"
        9->"Oct"
        10->"Nov"
        11->"Dec"
        else -> {
            ""
        }
    }

package com.example.mylist.tools

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.date.PersianDateImpl
import java.util.*

fun formatString(
        label:String,
        number: String,
        unitRelativeSizeFactor: Float = 0.7f
    ): SpannableString {
        val spannableString = SpannableString("${FaNum.convert(number)} $label")

        spannableString.setSpan(
            RelativeSizeSpan(unitRelativeSizeFactor),
            spannableString.indexOf(label),
            spannableString.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }


fun milliSecToPersianCal(dateLong:Long): PersianDateImpl {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dateLong
    val persianDate = PersianDateImpl()
    persianDate.setDate(dateLong)
    //persianDate.persianDay.minutes
    return persianDate
}


fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


fun getDatePicker(context: Context?): PersianDatePickerDialog {
    return PersianDatePickerDialog(context)
        .setPositiveButtonString("تایید")
        .setNegativeButton("لغو")
        .setTodayButton("امروز")
        .setTodayButtonVisible(true)
        .setMinYear(PersianDatePickerDialog.THIS_YEAR)
        .setShowInBottomSheet(false)
        .setMaxYear(1410)
        .setActionTextColor(Color.BLACK)
        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
}

fun persianDateToCalendar(persianDate:PersianPickerDate):Calendar{
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR,persianDate.gregorianYear)
    calendar.set(Calendar.MONTH,persianDate.gregorianMonth-1)
    calendar.set(Calendar.DAY_OF_MONTH,persianDate.gregorianDay)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    return  calendar
}




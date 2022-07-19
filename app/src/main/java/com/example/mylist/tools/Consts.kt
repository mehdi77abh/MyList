package com.example.mylist.tools

import android.content.Context
import android.graphics.Color
import com.example.mylist.R
import com.example.mylist.data.group.Colour
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import java.util.*

const val EXTRA_DATA ="data"
const val EXTRA_INTENT_ITEM ="intentItem"
const val EXTRA_VARIABLE="variable"

fun getColours():ArrayList<Colour>{
    val colours = ArrayList<Colour>()
    colours.add(Colour(R.color.color1 ))
    colours.add(Colour(R.color.color2 ))
    colours.add(Colour(R.color.color4 ))
    colours.add(Colour(R.color.color6 ))
    colours.add(Colour(R.color.color7 ))

    return colours
}
fun getDrawables():ArrayList<Int>{
    val list = ArrayList<Int>()
    list.add(R.drawable.group_chat)
    list.add(R.drawable.book)
    list.add(R.drawable.board_meeting)
    list.add(R.drawable.book_1)
    list.add(R.drawable.breakfast)
    list.add(R.drawable.buyer)
    list.add(R.drawable.call)
    list.add(R.drawable.chating)
    list.add(R.drawable.chatting)
    list.add(R.drawable.computer)
    list.add(R.drawable.conversation)
    list.add(R.drawable.conversation_1)
    list.add(R.drawable.download)
    list.add(R.drawable.group)
    list.add(R.drawable.hard_work)
    list.add(R.drawable.house)
    list.add(R.drawable.ideas)
    list.add(R.drawable.incoming_call)
    list.add(R.drawable.no_wifi)
    list.add(R.drawable.outgoing_call)
    list.add(R.drawable.passenger)
    list.add(R.drawable.phone_call)
    list.add(R.drawable.routine)
    list.add(R.drawable.schedule)
    list.add(R.drawable.shop)
    list.add(R.drawable.shopping_bag)
    list.add(R.drawable.student)
    list.add(R.drawable.study_1)
    list.add(R.drawable.study_2)
    list.add(R.drawable.travel)
    list.add(R.drawable.travel_luggage)
    list.add(R.drawable.treadmill)
    list.add(R.drawable.upload)
    list.add(R.drawable.upload_1)
    list.add(R.drawable.voice_message)
    list.add(R.drawable.washing_machine)
    list.add(R.drawable.wifi_connection)
    list.add(R.drawable.working)
    list.add(R.drawable.working_at_home_1)
    list.add(R.drawable.workout)
    list.add(R.drawable.workspace)
return list
}







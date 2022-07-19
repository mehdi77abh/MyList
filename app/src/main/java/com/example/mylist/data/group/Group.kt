package com.example.mylist.data.group

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "group_table")
@Parcelize
data class Group(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val title:String,
    val color:Int,
    val image:Int,
    var counter:Int,
    val isStar:Boolean=false
):Parcelable




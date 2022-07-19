package com.example.mylist.data.task

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tasks_tbl")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val groupId:Int,
    val title:String,
    val description:String,
    @ColumnInfo(name = "is_complete")
    var isComplete:Boolean=false,
    val dateLong:Long,
    val date:String,
    val color:Int,
):Parcelable


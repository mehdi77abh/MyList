package com.example.mylist.data.datebase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mylist.data.group.Group
import com.example.mylist.data.task.Task

@Database(entities = [Group::class, Task::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGroupDao(): GroupDao
    abstract  fun getTaskDao():TaskDao

    companion object {
        private var databaseInstance: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase {
            databaseInstance = databaseInstance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,"mtList.db"
            ).allowMainThreadQueries().build()
            return databaseInstance!!
        }

        fun destroyInstance() {
            databaseInstance = null
        }
    }

}
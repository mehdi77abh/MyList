package com.example.mylist.views.report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.task.Task

class ReportViewModel(val appDatabase: AppDatabase) : ViewModel() {
    private val groupDao = appDatabase.getGroupDao()
    private val taskDao = appDatabase.getTaskDao()
    val reportTag = "ReportTag"
    val percentOfSuccessTask = MutableLiveData<Int>()

    init {

        taskDao.getAllTaskNumber(true).observeForever { isComplete ->
            taskDao.getAllTaskNumber(false).observeForever { notComplete ->
                val all = isComplete + notComplete
                Log.i(reportTag, "Report View Model: " + all)
                Log.i(reportTag, "is Complete: " + isComplete)
                Log.i(reportTag, "not Complete: " + notComplete)

                Log.i(reportTag, "Percent: " + (isComplete * 100 / all).toDouble())

                percentOfSuccessTask.value = (isComplete * 100 / all)

            }
        }
    }

    fun getNumberOfGroups(): LiveData<Int> {
        return groupDao.getAllGroupsNumber()
    }

    fun getNumberOfTask(isComplete: Boolean): LiveData<Int> {
        return taskDao.getAllTaskNumber(isComplete)
    }

    fun getCompleteTasks(): LiveData<List<Task>> {
        return taskDao.getCompleteTasks()
    }

    fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
    fun getNumberOfAllTasks(): LiveData<Int> {
        return taskDao.getNumberOfAllTasks()
    }

    fun deleteTask(task:Task){
        taskDao.deleteTask(task)
    }


}
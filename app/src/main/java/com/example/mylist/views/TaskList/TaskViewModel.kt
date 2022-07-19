package com.example.mylist.views.TaskList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.group.Group
import com.example.mylist.data.task.Task

class TaskViewModel(val appDataBase:AppDatabase): ViewModel() {

    private val taskDao = appDataBase.getTaskDao()
    private  val groupDao = appDataBase.getGroupDao()
    fun insertTask(task: Task){
        taskDao.insertTask(task)
    }
    fun deleteTask(task: Task){
        taskDao.deleteTask(task)

    }
    fun clearTask(groupId: Int){
        taskDao.clearTasks(groupId)
    }
    fun clearCompleteTasks(){
        taskDao.clearTasks()
    }
    fun getCompleteTasks():LiveData<List<Task>>{
        return  taskDao.getCompleteTasks()
    }
    fun getTasks(groupId:Int ): LiveData<List<Task>> {
        return  taskDao.getNotCompleteTasks(groupId)
    }
    fun updateTask(task:Task){
        taskDao.updateTask(task)
    }

    fun updateGroup(group: Group){
        groupDao.updateGroup(group)
    }

    fun searchTask(query:String,groupId: Int):LiveData<List<Task>>{
        return taskDao.searchTasks(query,groupId,false)
    }
    fun getTasksListForCalendar (dateLong:Long):LiveData<List<Task>>{
        return taskDao.getTasksCalendar(dateLong)
    }
    fun getNotCompleteTasks(): LiveData<List<Task>> {
        return taskDao.getNotCompleteTasks()
    }
    fun getGroups(): LiveData<List<Group>> {
        return  groupDao.getAllGroup()
    }



}
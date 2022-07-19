package com.example.mylist.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.group.Group

class HomeViewModel(database: AppDatabase): ViewModel() {
    private val groupDao = database.getGroupDao()
    private val taskDao = database.getTaskDao()
    fun getAllGroups():LiveData<List<Group>> =groupDao.getAllGroup()

    fun saveGroup(group:Group){
        groupDao.insertGroup(group)
    }

    fun updateGroup(group: Group){
        groupDao.updateGroup(group)
    }

    fun deleteGroup(group: Group){
        taskDao.clearTasks(group.id!!)
        groupDao.deleteGroup(group)
    }

    fun deleteAllGroups(){
        groupDao.deleteAllGroups()
        taskDao.clearTasks()
    }

    fun searchGroups(query :String):LiveData<List<Group>>{
    return groupDao.search(query)
    }

}
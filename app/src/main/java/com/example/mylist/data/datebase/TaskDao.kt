package com.example.mylist.data.datebase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mylist.data.task.Task

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT * FROM tasks_tbl WHERE is_complete=1 ORDER BY dateLong")
    fun getCompleteTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks_tbl WHERE is_complete=0 AND dateLong=:dateLong ORDER BY dateLong")
    fun getTasksCalendar(dateLong: Long): LiveData<List<Task>>

    @Query("SELECT * FROM tasks_tbl WHERE groupId=:groupId AND is_complete=0 ORDER BY dateLong")
    fun getNotCompleteTasks(groupId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM tasks_tbl WHERE is_complete=0 ORDER BY dateLong")
    fun getNotCompleteTasks():LiveData<List<Task>>

    @Query("SELECT * FROM tasks_tbl WHERE groupId= :groupId AND is_complete= :isComplete AND title OR description LIKE '%' || :q || '%' ORDER BY dateLong")
    fun searchTasks(q: String, groupId: Int, isComplete: Boolean): LiveData<List<Task>>

    @Query("DELETE FROM tasks_tbl WHERE groupId=:groupId")
    fun clearTasks(groupId: Int)

    @Query("DELETE FROM TASKS_TBL")
    fun clearTasks()


    @Query("DELETE FROM TASKS_TBL WHERE is_complete=1")
    fun clearCompleteTasks()

    @Query("SELECT COUNT(*) FROM tasks_tbl WHERE is_complete=:isComplete")
    fun getAllTaskNumber(isComplete: Boolean):LiveData<Int>

    @Query("SELECT COUNT(*) FROM tasks_tbl")
    fun getNumberOfAllTasks():LiveData<Int>
}
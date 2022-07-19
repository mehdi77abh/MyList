package com.example.mylist.data.datebase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mylist.data.group.Group

@Dao
interface GroupDao {
    @Insert
    fun insertGroup(group: Group)

    @Delete
    fun deleteGroup(group: Group)

    @Update
    fun updateGroup(group: Group)

    @Query("SELECT * FROM group_table ORDER BY isStar = 0")
    fun getAllGroup(): LiveData<List<Group>>

    @Query("DELETE FROM group_table")
    fun deleteAllGroups()

    @Query("SELECT * FROM group_table WHERE title LIKE '%' || :q || '%'")
    fun search(q :String?):LiveData<List<Group>>


    @Query("SELECT COUNT(*) FROM group_table ")
    fun getAllGroupsNumber():LiveData<Int>



}
package com.codelabs.state

import androidx.room.*
import java.util.concurrent.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<TasksLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: TasksLocal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(water: WaterCups)

    @Query("DELETE FROM tasks WHERE task = :del_task")
     fun remove(del_task: String)

    @Delete
    fun delete(task: TasksLocal)

     @Update
     fun update (task: TasksLocal)

    @Query("DELETE FROM tasks")
     fun deleteAll()

     @Query("SELECT * FROM cups WHERE id = 1")
     fun getNumCups():  List<WaterCups>

    @Update
    fun updateCups(cups: WaterCups)
}
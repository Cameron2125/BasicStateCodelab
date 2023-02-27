package com.codelabs.state

import androidx.annotation.WorkerThread
import com.google.android.gms.tasks.Task
import java.util.concurrent.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TaskRespository(private val taskDao: TaskDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.

    suspend fun insert(task: TasksLocal) {
        taskDao.insert(task)
    }

    suspend fun remove(task: TasksLocal) {
        taskDao.remove(task.task!!)
    }


    suspend fun getAll(): List<TasksLocal> {
        return taskDao.getTasks()
    }


    suspend fun getCups(): List<WaterCups> {
        return taskDao.getNumCups()
    }

}
package com.codelabs.state

import android.app.Application
import com.codelabs.state.TaskListDataBase.Companion.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TasksApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy {TaskListDataBase.getDatabase(this)}
    val repository by lazy { TaskRespository(database.taskDao()) }
}
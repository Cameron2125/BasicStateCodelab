package com.codelabs.state

import android.content.Context
import androidx.compose.ui.text.android.animation.SegmentType
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [TasksLocal::class, WaterCups::class], version = 3, exportSchema = false)
public abstract class TaskListDataBase : RoomDatabase() {



    abstract fun taskDao(): TaskDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TaskListDataBase? = null

        fun getDatabase(context: Context): TaskListDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskListDataBase::class.java,
                    "TaskDataBase2"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codelabs.state

import android.content.Context
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.state.BuildConfig.DEBUG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WellnessViewModel() : ViewModel() {
    /**
     * Don't expose the mutable list of tasks from outside the ViewModel.
     * Instead define _tasks and tasks. _tasks is internal and mutable inside the ViewModel.
     * tasks is public and read-only.
     */
    private val _tasks =  mutableStateListOf<TasksLocal>()
    private lateinit var doa: TaskDao
    var count = mutableStateOf<Int>(0)


    val tasks: List<TasksLocal>
        get() = _tasks

    fun oneTime(){
        var water = WaterCups(num_cups = 0, id = 1)
        GlobalScope.launch(Dispatchers.IO) {


            try {
                doa.insert(water)
            } catch (ec: Exception) {

                Log.w("water count", ec.message!!)
            }


        }

    }
    fun createDoa(context1: Context){

        doa = TaskListDataBase.getDatabase(context1).taskDao()
    }

    // loads the tasks already put in back into the ui
    fun upDateCount(){

        count.value = count.value!!.plus(1)
        var water = WaterCups(num_cups = count.value!!, id = 1)
        GlobalScope.launch(Dispatchers.IO) {


            try {

                doa.updateCups(water)
            } catch (ec: Exception) {

                Log.w("count insert error", ec.message!!)
            }


        }

    }
    fun reloadCount() {

        GlobalScope.launch(Dispatchers.IO) {
            var cupList = doa.getNumCups()


            try {
                count.value = cupList[0].num_cups
            } catch (ec: Exception) {

                Log.w("count insert error", ec.message!!)
            }


        }
    }
    fun reload(){

        try {
            GlobalScope.launch(Dispatchers.IO){
                var taskList = doa.getTasks()

                for (task in taskList){
                    try {
                        _tasks.add(task)
                    }catch(ec: Exception){

                        Log.w("viewmodel insert error", ec.message!!)
                    }


                }


            }
        }catch(ex: Exception){
            Log.w("Database error", ex.toString())
        }

    }
    fun remove(item: TasksLocal) {

        viewModelScope.launch(Dispatchers.IO){
        doa.delete(item)

        }


        _tasks.remove(item)
    }
    fun add(task:String ){
        val adder = TasksLocal(task = task,checked = false)
        _tasks.add(adder)
        viewModelScope.launch(Dispatchers.IO){

            try {
                doa.insert(adder)
            }
            catch(ec: Exception){

                Log.w("info" , ec.message!!)
            }

        }


    }
    fun changeTaskChecked(item: TasksLocal, checked: Boolean) =
        tasks.find { it.id == item.id }?.let { task ->
            task.checked = task.checked.not()

        }
}


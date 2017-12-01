package com.tinyxu.lib.androidarch.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.tinyxu.lib.androidarch.model.Task

/**
 * Created by xuzhitao on 2017/11/30.
 *
 * @author xuzhitao
 */
class TaskViewModel: ViewModel() {
    /**
     * task list data，can observer
     */
    val mTaskList: MutableLiveData<MutableList<Task>> = MediatorLiveData<MutableList<Task>>()
    val LOADING_EVENT: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        LOADING_EVENT.value = false
    }

    // refresh. To refresh data from dataSource, for example network or db.
    // update. View update data.

    fun updateTasks(task: Task) {
        val tasks = mTaskList.value
        if (tasks != null && !tasks.isEmpty()) {
            for (t in tasks) {
                if (t == task) {
                    // to update persisitence data
                }
            }
        }
    }

    fun refreshTasks() {
        LOADING_EVENT.value = true
        Thread(Runnable {
            Log.d("xuzhitao1", "run thread load data")
            val tasks = mutableListOf<Task>()
            for (i in 1..20) {
                Thread.sleep(500)
                val t = Task("title-$i", "I am task.", false)
                tasks.add(t)
            }

            Log.d("xuzhitao1", "run thread load data finish, and postvalue")
            mTaskList.postValue(tasks)
            LOADING_EVENT.postValue(false)
        }).start()
    }
}

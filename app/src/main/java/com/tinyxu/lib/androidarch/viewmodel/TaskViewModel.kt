package com.tinyxu.lib.androidarch.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.tinyxu.lib.androidarch.extension.add
import com.tinyxu.lib.androidarch.extension.addAll
import com.tinyxu.lib.androidarch.extension.clear
import com.tinyxu.lib.androidarch.extension.reset
import com.tinyxu.lib.androidarch.model.Task

/**
 * Created by xuzhitao on 2017/11/30.
 *
 * @author xuzhitao
 */
class TaskViewModel: BaseViewModel() {
    /**
     * task list data，can observer
     */
    val mTaskList: MutableLiveData<MutableList<Task>> = MutableLiveData<MutableList<Task>>()

    init {
        val t = Task("title-100", "I am task.", false)
//        mTaskList.value?.add(t)
        mTaskList.add(t)
    }

    fun filterTasks(value: Int) {
        var index = value
        val result = mutableListOf<Task>()
        while (index < mTaskList.value!!.size) {
            result.add(mTaskList.value!!.get(index))
            index += value
        }
        mTaskList.reset(result)
    }

    // refresh. To refresh data from dataSource, for example network or db.
    // update. View update data.

    // TODO ugly, List的一些常用操作每次都要写一遍岂不是太麻烦
    fun updateTasks(task: Task) {
        val tasks = mTaskList.value
        if (tasks != null && !tasks.isEmpty()) {
            for (t in tasks) {
                if (t == task) {
                    // to update persistence data
                }
            }
        }
    }

    // TODO ugly, List的一些常用操作每次都要写一遍岂不是太麻烦
    fun refreshTasks() {
        IS_LOADING.value = true
        Thread(Runnable {
            Log.d("xuzhitao1", "run thread load data")
            val tasks = mutableListOf<Task>()
            for (i in 1..20) {
                Thread.sleep(100)
                val t = Task("title-$i", "I am task.", false)
                tasks.add(t)
            }

            Log.d("xuzhitao1", "run thread load data finish, and postvalue")
//            mTaskList.postValue(tasks)
            mTaskList.reset(tasks)

            IS_LOADING.postValue(false)
        }).start()
    }

    // TODO ugly, List的一些常用操作每次都要写一遍岂不是太麻烦
    fun appendTasks() {
        IS_LOADING.value = true
        Thread(Runnable {
            Log.d("xuzhitao1", "run thread load data")
            val tasks = mutableListOf<Task>()
            for (i in 1..10) {
                Thread.sleep(100)
                val t = Task("title-${i + 100}", "I am task.", false)
                tasks.add(t)
            }

            Log.d("xuzhitao1", "run thread load data finish, and postvalue")
            if (mTaskList.value == null) {
                mTaskList.value = mutableListOf()
            }

//            mTaskList.value?.addAll(tasks)
//            mTaskList.postValue(mTaskList.value)
            mTaskList.addAll(tasks)

            IS_LOADING.postValue(false)
        }).start()
    }

    // TODO ugly, List的一些常用操作每次都要写一遍岂不是太麻烦
    fun clearTasks() {
//        mTaskList.value?.clear()
//        // TODO ugly , 修改了value为什么不自动触发监听，只有setValue和postValue才能触发
//        mTaskList.value = mTaskList.value
        mTaskList.clear()
    }
}

package com.tinyxu.lib.androidarch.extension

import android.arch.lifecycle.MutableLiveData
import android.os.Looper

/**
 * Created by xuzhitao on 2017/12/4.
 *
 * @author xuzhitao
 */
fun <T> MutableLiveData<MutableList<T>>.clear() {
    var result = this.value
    if (result == null) {
        result = mutableListOf()
    }
    result.clear()
    if (isMainThread()) {
        this.value = result
    } else {
        this.postValue(result)
    }
}

fun <T> MutableLiveData<MutableList<T>>.add(item : T) {
    var result = this.value
    if (result == null) {
        result = mutableListOf()
    }
    result.add(item)
    if (isMainThread()) {
        this.value = result
    } else {
        this.postValue(result)
    }
}

fun <T> MutableLiveData<MutableList<T>>.addAll(itemList : List<T>) {
    var result = this.value
    if (result == null) {
        result = mutableListOf()
    }

    result.addAll(itemList)
    if (isMainThread()) {
        this.value = result
    } else {
        this.postValue(result)
    }
}

fun <T> MutableLiveData<MutableList<T>>.reset(itemList : List<T>) {
    val result = mutableListOf<T>()
    result.addAll(itemList)

    if (isMainThread()) {
        this.value = result
    } else {
        this.postValue(result)
    }
}

fun <T> MutableLiveData<MutableList<T>>.remove(item : T) {
    this.value?.remove(item)
    if (isMainThread()) {
        this.value = this.value
    } else {
        this.postValue(this.value)
    }
}

fun <T> MutableLiveData<MutableList<T>>.removeAt(position : Int) {
    this.value?.removeAt(position)
    if (isMainThread()) {
        this.value = this.value
    } else {
        this.postValue(this.value)
    }
}

fun isMainThread(): Boolean {
    return Thread.currentThread() == Looper.getMainLooper().thread
}

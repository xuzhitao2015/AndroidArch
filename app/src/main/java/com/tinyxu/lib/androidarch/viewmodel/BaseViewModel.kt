package com.tinyxu.lib.androidarch.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by xuzhitao on 2017/12/4.
 *
 * @author xuzhitao
 */
open class BaseViewModel: ViewModel() {
    val IS_LOADING: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        IS_LOADING.value = false
    }
}
package com.tinyxu.lib.androidarch.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by xuzhitao on 2017/12/4.
 *
 * @author xuzhitao
 */
abstract class BaseRecycleAdapter(): RecyclerView.Adapter<DataBindingAdapter.DataBindingViewHolder>() {
    var mDataList: MutableList<out Any> = arrayListOf()

    fun isSameDataObject (dataList: MutableList<out Any>?): Boolean {
        if (dataList != null && dataList == mDataList) {
            return true
        }
        return false
    }
}

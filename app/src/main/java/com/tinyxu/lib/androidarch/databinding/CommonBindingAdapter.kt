package com.tinyxu.lib.androidarch.databinding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.tinyxu.lib.androidarch.view.adapter.DataBindingAdapter

/**
 * Created by xuzhitao on 2017/12/1.
 *
 * @author xuzhitao
 */
@BindingAdapter("android:bindItemLayoutResId", "android:bindItemDataBindingBR", "android:bindDataList")
fun bindRecycleAdapter (recyclerView: RecyclerView, bindItemLayoutResId: Int, bindItemDataBindingBR: Int, bindDataList: MutableList<out Any>) {
    val adapter = DataBindingAdapter(recyclerView.context, bindItemLayoutResId, bindItemDataBindingBR)
    adapter.mDataList = bindDataList
    recyclerView.adapter = adapter
}
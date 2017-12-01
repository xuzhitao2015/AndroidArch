package com.tinyxu.lib.androidarch.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding



/**
 * Created by xuzhitao on 2017/12/1.
 *
 * @author xuzhitao
 */
class DataBindingAdapter(internal var mContext: Context, internal var mLayoutId: Int, internal var mVarId: Int, private val mData: MutableList<out Any>)
    : RecyclerView.Adapter<DataBindingAdapter.DataBindingViewHolder>() {
    var onBindingViewHolderListener: OnBindingViewHolderListener? = null
    var itemClickListener: ItemClickListener? = null

    internal var headerView: View? = View(mContext)
    internal var footerView: View? = View(mContext)
    internal var haveHeader = false
    internal var haveFooter = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        when (viewType) {
            TYPE_HEADER -> return DataBindingViewHolder(headerView?:View(mContext), viewType)
            TYPE_FOOTER -> return DataBindingViewHolder(footerView?:View(mContext), viewType)
            TYPE_NORMAL -> return DataBindingViewHolder(View.inflate(mContext, mLayoutId, null), viewType)
            else -> return DataBindingViewHolder(View.inflate(mContext, mLayoutId, null), viewType)
        }
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        when (holder.viewType) {
            TYPE_HEADER -> {
            }
            TYPE_FOOTER -> {
            }
            else -> {
                val binding = DataBindingUtil.bind<ViewDataBinding>(holder.itemView)
                val data: Any?
                if (haveHeader) {
                    data = mData[position - 1]
                } else {
                    data = mData[position]
                }

                if (itemClickListener != null) {
                    holder.itemView.setOnClickListener { itemClickListener!!.itemClick(holder.itemView, position) }
                }

                binding.setVariable(mVarId, data)
                binding.executePendingBindings()
                if (onBindingViewHolderListener != null) {
                    onBindingViewHolderListener!!.onHolderBinding(holder, position)
                }
            }
        }

    }

    /**
     * 调用之后请调用NotifyDataSetChange 如果在setAdapter之后
     * @param view
     */
    fun addFooterView(view: View) {
        haveFooter = true
        footerView = view
    }

    /**
     * 调用之后请调用NotifyDataSetChange 如果在setAdapter之后
     * @param view
     */
    fun addHeaderView(view: View) {
        haveHeader = true
        headerView = view
    }

    fun removeFooterView() {
        footerView = null
        haveFooter = false
    }

    fun cleanData() {
        mData.clear()
        notifyDataSetChanged()
    }

    val allData: List<*>
        get() = mData

//    fun addData(data: List<Any>) {
//        mData.addAll(data)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        var extraCount = 0
        if (haveHeader) {
            extraCount++
        } else if (haveFooter) {
            extraCount++
        }
        return mData.size + extraCount
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && haveHeader) {
            return TYPE_HEADER
        } else if (position == mData.size && haveFooter) {
            return TYPE_FOOTER
        } else {
            return TYPE_NORMAL
        }
    }

    inner class DataBindingViewHolder(itemView: View, internal var viewType: Int) : RecyclerView.ViewHolder(itemView)

    interface ItemClickListener {
        fun itemClick(view: View, position: Int)
    }

    interface OnBindingViewHolderListener {
        fun onHolderBinding(holder: DataBindingViewHolder, position: Int)
    }

    companion object {
        val TYPE_HEADER = 1
        val TYPE_FOOTER = 2
        val TYPE_NORMAL = 0
    }
}

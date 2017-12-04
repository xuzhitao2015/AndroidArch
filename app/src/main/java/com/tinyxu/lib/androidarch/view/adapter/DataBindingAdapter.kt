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
class DataBindingAdapter(internal var mContext: Context, internal var mLayoutId: Int, internal var mVarId: Int)
    : BaseRecycleAdapter() {
    var mItemClickListener: ItemClickListener? = null

    internal var mHeaderView: View? = View(mContext)
    internal var mFooterView: View? = View(mContext)
    internal var mHaveHeader = false
    internal var mHaveFooter = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        when (viewType) {
            TYPE_HEADER -> return DataBindingViewHolder(mHeaderView ?:View(mContext), viewType)
            TYPE_FOOTER -> return DataBindingViewHolder(mFooterView ?:View(mContext), viewType)
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
                if (mHaveHeader) {
                    data = mDataList[position - 1]
                } else {
                    data = mDataList[position]
                }

                if (mItemClickListener != null) {
                    holder.itemView.setOnClickListener { mItemClickListener!!.itemClick(holder.itemView, position) }
                }

                binding.setVariable(mVarId, data)
                binding.executePendingBindings()
            }
        }
    }

    /**
     * 调用之后请调用NotifyDataSetChange 如果在setAdapter之后
     * @param view
     */
    fun addFooterView(view: View) {
        mHaveFooter = true
        mFooterView = view
    }

    /**
     * 调用之后请调用NotifyDataSetChange 如果在setAdapter之后
     * @param view
     */
    fun addHeaderView(view: View) {
        mHaveHeader = true
        mHeaderView = view
    }

    fun removeFooterView() {
        mFooterView = null
        mHaveFooter = false
    }

    fun cleanData() {
        mDataList.clear()
        notifyDataSetChanged()
    }

    val allData: List<*>
        get() = mDataList

//    fun addData(data: List<Any>) {
//        mData.addAll(data)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        var extraCount = 0
        if (mHaveHeader) {
            extraCount++
        } else if (mHaveFooter) {
            extraCount++
        }
        return mDataList.size + extraCount
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && mHaveHeader) {
            return TYPE_HEADER
        } else if (position == mDataList.size && mHaveFooter) {
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

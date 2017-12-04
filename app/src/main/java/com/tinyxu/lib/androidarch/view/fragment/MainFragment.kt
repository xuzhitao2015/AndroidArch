package com.tinyxu.lib.androidarch.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.tinyxu.lib.androidarch.BR
import com.tinyxu.lib.androidarch.R
import com.tinyxu.lib.androidarch.databinding.bindRecycleAdapter
import com.tinyxu.lib.androidarch.model.Task
import com.tinyxu.lib.androidarch.view.adapter.BaseRecycleAdapter
import com.tinyxu.lib.androidarch.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by xuzhitao on 2017/11/30.
 *
 * @author xuzhitao
 */
class MainFragment: Fragment() {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var mViewMode: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewMode = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        mViewMode.mTaskList.observe(this, Observer { tasks : MutableList<Task>? ->
            Log.d("xuzhitao1", "on observe, ${tasks?.size}")
            if (recycleView.adapter == null) {
                Log.d("xuzhitao1", "on observe, set adapter")
                bindRecycleAdapter(recycleView, R.layout.item_task_list, BR.itemData, tasks?: mutableListOf<Task>())
            } else {
                Log.d("xuzhitao1", "on observe, notify adapter")
                if (recycleView.adapter is BaseRecycleAdapter) {
                    val isSame = (recycleView.adapter as BaseRecycleAdapter).isSameDataObject(tasks)
                    if (isSame) {
                        recycleView.adapter.notifyDataSetChanged()
                    } else {
                        bindRecycleAdapter(recycleView, R.layout.item_task_list, BR.itemData, tasks?: mutableListOf<Task>())
                    }
                }
            }
        })
        mViewMode.IS_LOADING.observe(this, Observer { loading: Boolean?  ->
            if (loading == true) {
                progressbar.visibility = View.VISIBLE
            } else {
                progressbar.visibility = View.GONE
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_main, container, false)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView.layoutManager = LinearLayoutManager(context)

        // 加载更多
        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val lm: LinearLayoutManager? = recyclerView.layoutManager as? LinearLayoutManager
                val totalItemCount = recyclerView.adapter.itemCount
                val lastVisibleItemPosition = lm?.findLastVisibleItemPosition()
                val visibleItemCount = recyclerView.childCount

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0
                        && totalItemCount > visibleItemCount) {
                    // 滚动到底部
                    mViewMode.appendTasks()
                }
            }
        })
        mViewMode.refreshTasks()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.tasks_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when(item?.itemId) {
                R.id.menu_filter    -> {
                    mViewMode.filterTasks(3)
                }
                R.id.menu_clear    -> {
                    mViewMode.clearTasks()
                }
                R.id.menu_refresh    -> {
                    mViewMode.refreshTasks()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

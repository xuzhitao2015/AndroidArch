package com.tinyxu.lib.androidarch.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import com.tinyxu.lib.androidarch.BR
import com.tinyxu.lib.androidarch.R
import com.tinyxu.lib.androidarch.databinding.bindRecycleAdapter
import com.tinyxu.lib.androidarch.model.Task
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_main, container, false)
        setHasOptionsMenu(true)
        mViewMode = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView.layoutManager = LinearLayoutManager(context)

        mViewMode.mTaskList.observe(this, Observer { tasks : MutableList<Task>? ->
            Log.d("xuzhitao1", "on observe, ${tasks?.size}")
            if (recycleView.adapter == null) {
                Log.d("xuzhitao1", "on observe, set adapter")
                bindRecycleAdapter(recycleView, R.layout.item_task_list, BR.itemData, tasks?: mutableListOf<Task>())
            } else {
                Log.d("xuzhitao1", "on observe, notify adapter")
                recycleView.adapter.notifyDataSetChanged()
            }
        })
        mViewMode.LOADING_EVENT.observe(this, Observer { loading: Boolean?  ->
            if (loading == true) {
            }
        })

        mViewMode.refreshTasks()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.tasks_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}

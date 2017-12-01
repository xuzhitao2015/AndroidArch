package com.tinyxu.lib.androidarch.extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by xuzhitao on 2017/11/30.
 *
 * @author xuzhitao
 */

fun FragmentManager.replaceFragmentInActivity(fragment: Fragment, containId: Int) {
    val tran = this.beginTransaction()
    tran.replace(containId, fragment)
    tran.commitAllowingStateLoss()
}

package com.tinyxu.lib.androidarch.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import com.tinyxu.lib.androidarch.R
import com.tinyxu.lib.androidarch.extension.replaceFragmentInActivity
import com.tinyxu.lib.androidarch.view.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        ab.setDisplayHomeAsUpEnabled(true)

        drawer_layout.setStatusBarBackground(R.color.colorPrimaryDark)

        setupViewFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewFragment() {
        val mainFragment: MainFragment? = supportFragmentManager.findFragmentById(R.id.contentFrame) as? MainFragment
        if (mainFragment == null) {
            supportFragmentManager.replaceFragmentInActivity(MainFragment.newInstance(), R.id.contentFrame)
        }
    }
}

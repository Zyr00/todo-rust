package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.android.adapters.ViewPageAdapter
import com.example.android.classes.Todo
import com.example.android.classes.Tuple
import com.example.android.fragments.ListTodoFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private val FRAGMENTS = arrayOf(
    Tuple(R.string.tab_name_1, R.drawable.ic_close_24, ListTodoFragment(ListTodoFragment.TODO)),
    Tuple(R.string.tab_name_2, R.drawable.ic_done_24, ListTodoFragment(ListTodoFragment.DONE))
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        val adapter = ViewPageAdapter(FRAGMENTS, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(FRAGMENTS[position].name)
            tab.setIcon(FRAGMENTS[position].icon)
        }.attach()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Todo.toIntent(null, FragmentHandlerActivity.TYPE_ADD, this,
                FragmentHandlerActivity::class.java)
            startActivity(intent);
        }
    }
}
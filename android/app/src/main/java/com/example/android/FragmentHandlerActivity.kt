package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android.classes.Todo
import com.example.android.fragments.AddEditTodoFragment
import com.example.android.fragments.DetailsFragment

class FragmentHandlerActivity : AppCompatActivity() {

    companion object {
        const val TYPE = "FRAGMENT_TYPE"
        const val TYPE_DETAILS = "FRAGMENT_TYPE_DETAILS"
        const val TYPE_ADD = "FRAGMENT_TYPE_ADD"
        lateinit var todo: Todo

        fun replaceFragment(supportFM: FragmentManager, frag: Fragment) {
            supportFM.beginTransaction().replace(R.id.container, frag).commitNow()
        }

        fun changeToDetails(): DetailsFragment {
            return DetailsFragment.newInstance(todo)
        }

        fun changeToAddEditTodos(): AddEditTodoFragment {
            return AddEditTodoFragment.newInstance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_handler_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            todo = Todo.fromIntent(intent);

            val frag = when (intent.getStringExtra(TYPE)) {
                TYPE_ADD -> { changeToAddEditTodos() }
                else -> { changeToDetails() }
            }
            replaceFragment(supportFragmentManager, frag);
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
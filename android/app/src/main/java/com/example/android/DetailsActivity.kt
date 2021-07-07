package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.classes.Todo
import com.example.android.fragments.DetailsFragment

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        if (savedInstanceState == null) {
            val todo = Todo(
                getStringFromIntent(Todo.ARG_TITLE),
                getStringFromIntent(Todo.ARG_DESC),
                intent.getBooleanExtra(Todo.ARG_DONE, false)
            )
            val frag = DetailsFragment.newInstance(todo);
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commitNow()
        }
    }

    private fun getStringFromIntent(name: String): String {
        var value = "Unknown"
        if (intent.getStringExtra(name) != null)
            value = intent.getStringExtra(name)!!
        return value
    }
}
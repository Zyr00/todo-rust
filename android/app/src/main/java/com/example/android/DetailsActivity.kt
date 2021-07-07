package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android.classes.Todo
import com.example.android.fragments.DetailsFragment

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            val todo = Todo.fromIntent(intent);
            val frag = DetailsFragment.newInstance(todo);
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit_todo -> {
                Toast.makeText(this, "Edit This fucker", Toast.LENGTH_SHORT)
                    .show();
                return true;
            }
            R.id.delete_todo -> {
                Toast.makeText(this, "Delete This fucker", Toast.LENGTH_SHORT)
                    .show();
                return true;
            }
            android.R.id.home -> {
                super.onBackPressed()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
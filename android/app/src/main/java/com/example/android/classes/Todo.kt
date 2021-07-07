package com.example.android.classes

import android.content.Context
import android.content.Intent
import android.os.Bundle

class Todo(_title: String?, _desc: String?, _done: Boolean?) {
    var title = _title ?: "Unknown"
    var desc = _desc ?: "Unknown"
    var done = _done ?: false

    companion object: IArgs {
        private const val ARG_TITLE = "TODO_TITLE_ARG"
        private const val ARG_DESC = "TODO_DESC_ARG"
        private const val ARG_DONE = "TODO_DONE_ARG"

        override fun fromArguments(arguments: Bundle): Todo {
            return Todo(
                arguments.getString(ARG_TITLE),
                arguments.getString(ARG_DESC),
                arguments.getBoolean(ARG_DONE)
            )
        }

        override fun toArguments(todo: Todo): Bundle = Bundle().apply {
            putString(ARG_TITLE, todo.title)
            putString(ARG_DESC, todo.desc)
            putBoolean(ARG_DONE, todo.done)
        }

        override fun <A> toIntent(todo: Todo, context: Context?, activity: Class<A>): Intent {
            return Intent(context, activity).apply {
                putExtra(ARG_TITLE, todo.title)
                putExtra(ARG_DESC, todo.desc)
                putExtra(ARG_DONE, todo.done)
            }
        }

        override fun fromIntent(intent: Intent?): Todo {
            return Todo(
                getStringFromIntent(intent, ARG_TITLE),
                getStringFromIntent(intent, ARG_DESC),
                intent?.getBooleanExtra(ARG_DONE, false)
            )
        }

        private fun getStringFromIntent(intent: Intent?, name: String): String {
            var value = "Unknown"
            if (intent?.getStringExtra(name) != null)
                value = intent.getStringExtra(name)!!
            return value
        }
    }
}
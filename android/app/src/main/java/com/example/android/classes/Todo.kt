package com.example.android.classes

import android.os.Bundle

class Todo(_title: String?, _desc: String?, _done: Boolean?) {
    companion object {
        const val ARG_TITLE = "TODO_TITLE_ARG"
        const val ARG_DESC = "TODO_DESC_ARG"
        const val ARG_DONE = "TODO_DONE_ARG"

        fun fromArguments(arguments: Bundle): Todo {
            return Todo(
                arguments.getString(ARG_TITLE),
                arguments.getString(ARG_DESC),
                arguments.getBoolean(ARG_DONE)
            )
        }

        fun toArguments(todo: Todo): Bundle = Bundle().apply {
            putString(ARG_TITLE, todo.title)
            putString(ARG_DESC, todo.desc)
            putBoolean(ARG_DONE, todo.done)
        }
    }

    var title = _title ?: "Unknown"
    var desc = _desc ?: "Unknown"
    var done = _done ?: false

}
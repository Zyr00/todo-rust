package com.example.android.classes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import com.example.android.FragmentHandlerActivity
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader

class Todo(_title: String?, _desc: String?, _done: Boolean?) {
    var title = _title ?: "Unknown"
    var desc = _desc ?: "Unknown"
    var done = _done ?: false

    companion object: IArgs<Todo> {
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

        override fun toArguments(value: Todo): Bundle = Bundle().apply {
            putString(ARG_TITLE, value.title)
            putString(ARG_DESC, value.desc)
            putBoolean(ARG_DONE, value.done)
        }

        override fun <A> toIntent(value: Todo?, type: String, context: Context?, activity: Class<A>): Intent {
            if (value == null) {
                return Intent(context, activity).apply { putExtra(FragmentHandlerActivity.TYPE, type) }
            }
            return Intent(context, activity).apply {
                putExtra(FragmentHandlerActivity.TYPE, type)
                putExtra(ARG_TITLE, value.title)
                putExtra(ARG_DESC, value.desc)
                putExtra(ARG_DONE, value.done)
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
            if (intent?.getStringExtra(name) != null) value = intent.getStringExtra(name)!!
            return value
        }

        fun handleTodo(todo: JSONObject): Todo {
            return Todo(todo.getString("title"), todo.getString("desc"),
                todo.getBoolean("done"))
        }
    }

    class Deserializer: ResponseDeserializable<Any> {
        override fun deserialize(content: String): Any? {
            val json = JSONObject(content)
            when {
                json.has("todo") -> {
                    val jsonTodo = json.getJSONObject("todo")
                    return handleTodo(jsonTodo)
                }
                json.has("todos") -> {
                    val jsonTodos = json.getJSONArray("todos")
                    val todos = mutableListOf<Todo>()
                    for (i in 0 until jsonTodos.length()) {
                        todos.add(handleTodo(jsonTodos.getJSONObject(i)))
                    }
                    return todos
                }
                else -> {
                    return null
                }
            }
        }
    }
}
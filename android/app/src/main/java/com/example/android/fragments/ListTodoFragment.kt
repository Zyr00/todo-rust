package com.example.android.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.adapters.ListAdapter
import com.example.android.classes.Todo
import com.example.android.tasks.ApiTask
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result

class ListTodoFragment(_todo: String) : Fragment(), ApiTask.ApiListener {

    private val todo: String = _todo

    companion object {
        const val TODO = "/todo/todo"
        const val DONE = "/todo/done"
        private lateinit var recyclerView: RecyclerView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler);

        ApiTask.setListener(this)
        ApiTask.get(todo, Todo.Deserializer())
    }

    override fun onSuccess(result: Any) {
        val todos = mutableListOf<Todo>()
        (result as MutableList<*>).forEach {
            todos.add(it as Todo)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(todos)
        }
    }

    override fun onFailure(result: Result.Failure<FuelError>) {
        Log.d("onFailure", "onFailure: ${result.error.message}")
    }

    override fun onOther(result: Result<Any, FuelError>) {
        Log.d("onOther", "onOther: $result")
    }
}
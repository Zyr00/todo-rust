package com.example.android.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.classes.Todo

class TodoViewModel: ViewModel() {
    private var _todo = MutableLiveData(Todo("", "", false))
    val todo: LiveData<Todo> = _todo

    fun updateTodo(todo: Todo) {
        _todo.value = todo
    }
}
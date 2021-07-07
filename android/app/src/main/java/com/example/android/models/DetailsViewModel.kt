package com.example.android.models

import androidx.lifecycle.ViewModel
import com.example.android.classes.Todo

class MainViewModel: ViewModel() {
    var todo: Todo = Todo("Unknown", "Unknown", false)

    fun updateTodo(_todo: Todo) {
        todo = _todo
    }
}
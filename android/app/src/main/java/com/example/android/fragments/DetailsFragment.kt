package com.example.android.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.R
import com.example.android.classes.Todo
import com.example.android.models.MainViewModel

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()

        fun newInstance(todo: Todo): DetailsFragment {
            val frag = DetailsFragment()
            frag.arguments = Todo.toArguments(todo)
            return frag;
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if (arguments != null)
            viewModel.updateTodo(Todo.fromArguments(requireArguments()))

        val todo = viewModel.todo
        val title = view.findViewById<TextView>(R.id.title)
        val desc = view.findViewById<TextView>(R.id.desc)

        title.text = todo.title
        desc.text = todo.desc
    }
}
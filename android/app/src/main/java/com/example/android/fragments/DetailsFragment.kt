package com.example.android.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.android.R
import com.example.android.classes.Todo
import com.example.android.models.TodoViewModel

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
        fun newInstance(todo: Todo): DetailsFragment {
            val frag = DetailsFragment()
            frag.arguments = Todo.toArguments(todo)
            return frag;
        }
    }

    private val viewModel: TodoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextView>(R.id.title)
        val desc = view.findViewById<TextView>(R.id.desc)

        if (arguments != null) {
            viewModel.updateTodo(Todo.fromArguments(requireArguments()))
            arguments = null
        }

        viewModel.todo.observe(viewLifecycleOwner, { todo ->
            title.text = todo.title
            desc.text = todo.desc
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit_todo -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, AddEditTodoFragment.newInstance(true))
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.delete_todo -> {
                Toast.makeText(context, "DELETE TODO", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
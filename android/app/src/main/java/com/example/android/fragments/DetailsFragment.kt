package com.example.android.fragments

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.android.FragmentHandlerActivity
import com.example.android.MainActivity
import com.example.android.R
import com.example.android.classes.Todo
import com.example.android.models.TodoViewModel
import com.example.android.tasks.ApiTask
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result

class DetailsFragment : Fragment(), ApiTask.ApiListener {

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
                ApiTask.setListener(this)
                ApiTask.delete("/todo/${viewModel.todo.value!!.id}")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPre() { }

    override fun onSuccess(result: Any) {
        Toast.makeText(context, "ELIMINATED", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun onFailure(result: Result.Failure<FuelError>) {
        Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
    }
}
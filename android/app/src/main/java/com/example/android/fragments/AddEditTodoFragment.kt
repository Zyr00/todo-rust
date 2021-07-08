package com.example.android.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android.R
import com.example.android.R.*
import com.example.android.classes.Todo
import com.example.android.models.TodoViewModel
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText

class AddEditTodoFragment : Fragment() {

    companion object {
        private const val FROM_FRAGMENT = "FROM_FRAGMENT"
        private lateinit var title: TextInputEditText
        private lateinit var desc: TextInputEditText
        private lateinit var done: MaterialCheckBox

        fun newInstance() = AddEditTodoFragment()
        fun newInstance(_fromFragment: Boolean): AddEditTodoFragment {
            val frag = AddEditTodoFragment()
            frag.arguments = Bundle().apply { putBoolean(FROM_FRAGMENT, _fromFragment) }
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
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_add_edit_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.text_input_title)
        desc = view.findViewById(R.id.text_input_desc)
        done = view.findViewById(R.id.checkbox_done)

        viewModel.todo.observe(viewLifecycleOwner, { todo ->
            title.setText(todo.title)
            desc.setText(todo.desc)
            done.isChecked = todo.done
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_edit_todo_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save_todo -> {
                val todo = Todo(title.text.toString(), desc.text.toString(), done.isChecked);
                viewModel.updateTodo(todo)
                if (arguments != null && requireArguments().getBoolean(FROM_FRAGMENT, false))
                    requireActivity().supportFragmentManager.popBackStack()
                else
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, DetailsFragment.newInstance())
                        .commit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
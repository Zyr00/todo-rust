package com.example.android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
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
    private var progressBar: ProgressBar? = null
    private var errorLayout: RelativeLayout? = null
    private var errorText: TextView? = null
    private var retryButton: AppCompatButton? = null

    companion object {
        const val TODO = "/todo/todo"
        const val DONE = "/todo/done"
        private lateinit var recyclerView: RecyclerView

        fun newInstance(bundle: Bundle): ListTodoFragment {
            val frag = ListTodoFragment(TODO)
            frag.arguments = bundle
            return frag
        }
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

        recyclerView = view.findViewById(R.id.recycler)
        progressBar = view.findViewById(R.id.progress_bar)
        errorText = view.findViewById(R.id.error_text)
        retryButton = view.findViewById(R.id.btn_retry)
        errorLayout = view.findViewById(R.id.error_layout)

        ApiTask.setListener(this)
        ApiTask.get(todo, Todo.Deserializer())
    }

    override fun onPre() {
        recyclerView.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out))
        updateProgressBar(false)
        updateError(true, showRetry = false, message = null)
    }

    override fun onSuccess(result: Any) {
        val todos = mutableListOf<Todo>()
        (result as MutableList<*>).forEach {
            todos.add(it as Todo)
        }

        updateProgressBar(true)
        if (todos.size <= 0) {
            updateError(false, showRetry = false, message = "No Todos Found")
            return
        }
        recyclerView.apply {
            startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(todos)
        }
    }

    override fun onFailure(result: Result.Failure<FuelError>) {
        updateProgressBar(true)
        updateError(false, showRetry = true, message = result.error.message.toString())
    }

    private fun updateError(remove: Boolean, showRetry: Boolean, message: String?) {
        if (remove) {
            errorLayout!!.visibility = View.GONE
            errorText!!.visibility = View.GONE
            retryButton!!.visibility = View.GONE
            return
        }
        errorLayout!!.visibility = View.VISIBLE
        errorText!!.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
            text = message
        }
        retryButton!!.apply {
            if (showRetry) visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
            setOnClickListener {
                ApiTask.get(todo, Todo.Deserializer())
            }
        }
    }

    private fun updateProgressBar(remove: Boolean) {
        if (remove) {
            progressBar!!.apply {
                startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out))
                visibility = View.GONE
            }
            return
        }
        progressBar!!.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
        }
    }
}
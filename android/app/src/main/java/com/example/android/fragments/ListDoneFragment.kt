package com.example.android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.adapters.ListAdapter
import com.example.android.classes.Todo

class ListDoneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler);

        val items = arrayOf(
            Todo("Item number ", "Description of the item Description of the item Description", true),
            Todo("Item number ", "Description of the item Description of the item Description of the item Description of the item", true),
            Todo("Item number ", "Description of the item Description of the item Description of the item Description of the item", true),
            Todo("Item number ", "Description of ", true),
            Todo("Item number ", "Description of ", true),
            Todo("Item number ", "Description of ", true),
            Todo("Item number ", "Description of ", true),
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(items)
        }
    }
}
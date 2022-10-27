package com.ezzy.todotask

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ezzy.todotask.adapter.CustomDropDownAdapter
import com.ezzy.todotask.adapter.RecyclerViewAdapter
import com.ezzy.todotask.databinding.ActivityMainBinding
import com.ezzy.todotask.models.TodoModel
import com.ezzy.todotask.models.TodoModelItem
import com.ezzy.todotask.service.UserModel
import com.ezzy.todotask.viewmodels.TodoViewModel
import com.ezzy.todotask.viewmodels.UserViewModel


class MainActivity : AppCompatActivity(), CustomDropDownAdapter.onSelected,
    RecyclerViewAdapter.RowClickListener {
    lateinit var userViewModel: UserViewModel
    lateinit var todoViewModel: TodoViewModel
    private lateinit var userModel: UserModel
    private lateinit var todoModel: TodoModel

    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = setContentView(this, R.layout.activity_main)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        initApiCallAndViewModel();
    }

    private fun initApiCallAndViewModel() {
        userViewModel.getUserList();
        todoViewModel.getTodoList();



        userViewModel.getUserObserver().observe(this, Observer<UserModel> {
            if (it != null) {
                userModel = it

                initSpinner(it)

            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })

        todoViewModel.getTodoObserver().observe(this, Observer<TodoModel> {
            if (it != null) {
                todoModel = it
                recyclerViewAdapter.setListData(ArrayList(it))
                recyclerViewAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })





        initRecyclerView()
    }

    private fun initSpinner(userModel: UserModel) {

        var userListName = ArrayList<String>();
        var sizeOfUserModel = userModel.size - 1;
        for (i in 0..sizeOfUserModel) {
            userListName.add(userModel[i].name)
        }


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, userListName
        )
        binding.mspinner.adapter = adapter

        binding.mspinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                Log.d("selected item is ", userModel[position].email)
                Log.d("item is selected", userModel[position].id.toString())

                todoViewModel.getOnlyTodoById(userModel[position].id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    override fun OnItemSelectedSpinner(position: Int) {
        Log.d("item is selected", userModel[position].id.toString())
        recyclerViewAdapter.sendUserID( userModel[position].id)
    }


    private fun initRecyclerView() {
        binding.todoRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
            val divider =
                DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }

    override fun onItemClickListener(todo: TodoModelItem) {

    }


}
package com.ezzy.todotask.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.ezzy.todotask.R
import com.ezzy.todotask.models.TodoModelItem
import kotlinx.android.synthetic.main.item_todo.view.*


class RecyclerViewAdapter(val listener: RowClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items = ArrayList<TodoModelItem>()

    fun setListData(data: ArrayList<TodoModelItem>) {

        this.items = data

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return MyViewHolder(inflater, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }

        holder.bind(items[position])

    }

    fun sendUserID(id: Int) {

        val tempList = arrayListOf<TodoModelItem>()

        items?.forEach { item ->
            items.filter {
                // filter condition
               it.id == id
                tempList.add(it)
            }


        setListData(tempList)
        notifyDataSetChanged()



        }

    }


    class MyViewHolder(view: View, val listener: RowClickListener) : RecyclerView.ViewHolder(view) {

        val title = view.textView
        val checked = view.checkBox


        fun bind(data: TodoModelItem) {
            title.text = data.title
            if (data.completed) {
                checked.isChecked = true
            } else {
                checked.isChecked = false
            }


//            deleteImage.setOnClickListener {
//                listener.onItemDeleteListener(position)
//            }


        }
    }

    interface RowClickListener {
        fun onItemClickListener(todo: TodoModelItem)
    }
}
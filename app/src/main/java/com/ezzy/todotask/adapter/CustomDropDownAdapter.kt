package com.ezzy.todotask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.adapters.AdapterViewBindingAdapter
import com.ezzy.todotask.R
import com.ezzy.todotask.service.UserModel

class CustomDropDownAdapter(
    val context: Context,
    var dataSource: UserModel,
    val listener: onSelected
) : BaseAdapter() {



    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_spinner_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = dataSource.get(position).name


//        listener.OnItemSelectedSpinner(position)

        return view
    }


    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView


        init {
            label = row?.findViewById(R.id.userName) as TextView
        }
    }

    interface onSelected {
        fun OnItemSelectedSpinner(position: Int)
    }




}

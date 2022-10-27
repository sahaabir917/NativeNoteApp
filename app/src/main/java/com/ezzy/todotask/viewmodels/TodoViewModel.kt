package com.ezzy.todotask.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ezzy.service.RetroInstance
import com.ezzy.service.RetroService
import com.ezzy.todotask.models.TodoModel
import com.ezzy.todotask.models.TodoModelItem
import com.ezzy.todotask.service.UserModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TodoViewModel(app: Application) : AndroidViewModel(app) {

    var todos: MutableLiveData<TodoModel> = MutableLiveData()
    var todosShow: MutableLiveData<TodoModel> = MutableLiveData()


    fun getTodoList() {

        val retroInstance  = RetroInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getTodoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getTodoListObserverRx())//give body

    }



    fun getTodoObserver(): MutableLiveData<TodoModel> {
        return todosShow
    }



    private fun getTodoListObserverRx(): Observer<TodoModel> {
        return object : Observer<TodoModel> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                Log.d("error",e.message.toString())
                todosShow.postValue(null)
                todos.postValue(null)

            }


            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }

            override fun onNext(t: TodoModel) {
                todosShow.postValue(t)
                todos.postValue(t)
                Log.d("users",todos.value.toString())
            }
        }
    }

    fun getOnlyTodoById(id: Int) {

        var totalTodos = todos.value!!.size-1;
//
        var tempTodoModel = ArrayList<TodoModelItem>()

        for(i in 0..totalTodos){
            if(todos.value!![i].userId == id){
                print("match found, ${todos.value!![i].title} ")
                tempTodoModel.add(todos.value!![i])
            }
        }

        var todeModel = TodoModel()
       todeModel.addAll(tempTodoModel)

        todosShow.postValue(todeModel)

    }

}
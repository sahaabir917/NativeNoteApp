package com.ezzy.todotask.viewmodels
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ezzy.service.RetroInstance
import com.ezzy.service.RetroService
import com.ezzy.todotask.service.UserModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserViewModel(app: Application) : AndroidViewModel(app) {
   var users: MutableLiveData<UserModel> = MutableLiveData()


    fun getUserList() {

        val retroInstance  = RetroInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getUserData()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(getUserListObserverRx())//give body

    }



   fun getUserObserver(): MutableLiveData<UserModel> {
      return users
   }



   private fun getUserListObserverRx(): Observer<UserModel> {
      return object : Observer<UserModel> {
         override fun onComplete() {
            //hide progress indicator .
         }

         override fun onError(e: Throwable) {
            Log.d("error",e.message.toString())
            users.postValue(null)
         }


         override fun onSubscribe(d: Disposable) {
            //start showing progress indicator.
         }

         override fun onNext(t: UserModel) {
            users.postValue(t)
            Log.d("users",t.toString())
         }
      }
   }

}
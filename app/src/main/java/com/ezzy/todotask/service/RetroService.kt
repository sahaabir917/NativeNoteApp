package com.ezzy.service

import com.ezzy.todotask.models.TodoModel
import com.ezzy.todotask.service.UserModel

import io.reactivex.Observable
import retrofit2.http.*

interface RetroService {

//    @Headers( "Content-Type:application/json" )
//    @POST("demo/login")
//    fun getLoginData(@Body body: LoginCredential): Observable<UserModel>


    @GET("users")
    fun getUserData() : Observable<UserModel>

    @GET("todos")
    fun getTodoList() : Observable<TodoModel>

}
package com.ezzy.todotask.models

data class TodoModelItem(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)
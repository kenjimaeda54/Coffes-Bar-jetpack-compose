package com.example.coffesbarcompose.data

data class DataOrException<T, Bool, E : Exception> (
    var data: T? = null,
    var isLoading: Bool? = null,
    val exception: E? = null,
)
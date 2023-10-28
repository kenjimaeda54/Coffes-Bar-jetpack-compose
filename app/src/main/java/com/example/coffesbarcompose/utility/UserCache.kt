package com.example.coffesbarcompose.utility

import com.example.coffesbarcompose.models.UserModel

interface UserCache {

    fun saveUser(user: UserModel)

    fun getUser(): UserModel?

    fun clearUser()

}
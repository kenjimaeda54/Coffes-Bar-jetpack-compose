package com.example.coffesbarcompose.models

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coffesbarcompose.utility.UserCache
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserCacheViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel(), UserCache {

    //estou usando moshi pra transformar a classe em json
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter(UserModel::class.java)

    @SuppressLint("CommitPrefEdits")
    override fun saveUser(user: UserModel) {
        sharedPreferences.edit().putString("user", adapter.toJson(user)).apply()
    }

    override fun getUser(): UserModel? {
        val json = sharedPreferences.getString("user", null) ?: return null
        return adapter.fromJson(json)
    }

    override fun clearUser() {
        sharedPreferences.edit().remove("user").apply()
    }


}
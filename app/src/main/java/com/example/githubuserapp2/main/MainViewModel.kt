package com.example.githubuserapp2.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp2.api.ApiConfig
import com.example.githubuserapp2.data.model.User
import com.example.githubuserapp2.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers= MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String){
        ApiConfig.apiInstance
            .getSearchUser(query)
            .enqueue(object: Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failed", t.message.toString())
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<User>>{
        return listUsers
    }

}
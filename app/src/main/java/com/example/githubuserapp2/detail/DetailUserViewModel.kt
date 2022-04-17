package com.example.githubuserapp2.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp2.api.ApiConfig
import com.example.githubuserapp2.data.local.entity.FavoriteUser
import com.example.githubuserapp2.data.local.room.FavoriteUserDao
import com.example.githubuserapp2.data.local.room.FavoriteUserDatabase
import com.example.githubuserapp2.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application){
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao?
    private var userDatabase: FavoriteUserDatabase? = FavoriteUserDatabase.getDatabase(application)

    init {
        userDao = userDatabase?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        ApiConfig.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful)
                        user.postValue(response.body())
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(id: Int, username: String, avatar_url: String){
        CoroutineScope(Dispatchers.IO).launch{
            val user = FavoriteUser(
                id,
                username,
                avatar_url
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFavoriteUser(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavoriteUser(id)
        }
    }
}
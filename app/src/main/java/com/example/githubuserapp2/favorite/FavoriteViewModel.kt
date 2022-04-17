package com.example.githubuserapp2.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp2.data.local.entity.FavoriteUser
import com.example.githubuserapp2.data.local.room.FavoriteUserDao
import com.example.githubuserapp2.data.local.room.FavoriteUserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDatabase: FavoriteUserDatabase? = FavoriteUserDatabase.getDatabase(application)

    init {
        userDao = userDatabase?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}
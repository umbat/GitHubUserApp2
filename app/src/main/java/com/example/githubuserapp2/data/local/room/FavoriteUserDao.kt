package com.example.githubuserapp2.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp2.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFavoriteUser(id: Int): Int
}
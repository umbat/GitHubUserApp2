package com.example.githubuserapp2.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp2.data.local.entity.FavoriteUser

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object{
        var INSTANCE : FavoriteUserDatabase? = null

        fun getDatabase(context: Context): FavoriteUserDatabase? {
            if (INSTANCE==null){
                synchronized(FavoriteUserDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserDatabase::class.java, "User.db").build()
                }
            }
            return INSTANCE
        }
    }
}
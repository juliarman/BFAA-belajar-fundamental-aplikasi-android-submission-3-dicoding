package com.juliarman.appsgithubuser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteRoomDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object{

        @Volatile

        private var INSTANCE: FavoriteRoomDatabase? = null


        @JvmStatic

        fun getDatabase(context: Context): FavoriteRoomDatabase{

            if (INSTANCE == null)

                synchronized(FavoriteRoomDatabase::class.java){

                    INSTANCE = Room.databaseBuilder(context.applicationContext,

                        FavoriteRoomDatabase::class.java, "favorite_database")
                        .build()

                }

            return INSTANCE as FavoriteRoomDatabase

        }



    }
}
package com.juliarman.appsgithubuser.db

import androidx.lifecycle.LiveData
import androidx.room.*



@Dao

interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite): Long


    @Update
    fun update(favorite: Favorite)


    @Delete
    fun delete(favorite: Favorite)


    @Query("SELECT * from favorite ORDER BY id ASC")

    fun getAllFavorite(): LiveData<List<Favorite>>

}
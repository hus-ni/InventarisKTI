package com.muhammadhusniabdillah.inventariskti.login

import androidx.room.*

@Dao
interface DaoLogin {
    @Query("SELECT * FROM TableLogin")
    fun readLogin(): MutableList<TableLogin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createLogin(login: TableLogin): Long

    @Update
    fun updateLogin(login: TableLogin): Int

    @Delete
    fun deleteLogin(login: TableLogin): Int

}
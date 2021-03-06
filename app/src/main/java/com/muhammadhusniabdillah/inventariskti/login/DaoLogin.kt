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

    @Query("select exists(select username from TableLogin where username = :user)")
    fun isUserExists(user: String): Boolean

    @Query("select exists(select * from TableLogin where username = :username and password = :password)")
    fun isThisUsernamePasswordExists(username: String, password: String): Boolean

}
package com.muhammadhusniabdillah.inventariskti.home

import androidx.room.*

@Dao
interface DaoHome {
    @Query("SELECT * FROM TableHome")
    fun readData(): MutableList<TableHome>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createData(home: TableHome): Long

    @Update
    fun updateData(home: TableHome): Int

    @Delete
    fun deleteData(home: TableHome): Int
}
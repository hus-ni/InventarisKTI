package com.muhammadhusniabdillah.inventariskti.home

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoHome {
    @Query("SELECT * FROM TableHome")
    fun readData(): Flow<MutableList<TableHome>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createData(home: TableHome): Long

    @Update
    fun updateData(home: TableHome): Int

    @Delete
    fun deleteData(home: TableHome): Int
}
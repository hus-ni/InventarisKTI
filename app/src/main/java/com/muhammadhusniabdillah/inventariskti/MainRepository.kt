package com.muhammadhusniabdillah.inventariskti

import androidx.annotation.WorkerThread
import com.muhammadhusniabdillah.inventariskti.home.DaoHome
import com.muhammadhusniabdillah.inventariskti.home.TableHome
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainRepository(private val homeDao: DaoHome) {

    val inventoryData: Flow<MutableList<TableHome>> = homeDao.readData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createData(data: TableHome) {
        GlobalScope.launch {
            homeDao.createData(data)
        }
    }
}
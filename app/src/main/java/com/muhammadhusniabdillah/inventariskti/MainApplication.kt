package com.muhammadhusniabdillah.inventariskti

import android.app.Application
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainApplication : Application() {

//    val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { InventarisDB.getInstance(this)}
    val repository by lazy { MainRepository(database!!.homeDao()) }
}
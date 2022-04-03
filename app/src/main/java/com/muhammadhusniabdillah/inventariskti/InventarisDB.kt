package com.muhammadhusniabdillah.inventariskti

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muhammadhusniabdillah.inventariskti.login.DaoLogin
import com.muhammadhusniabdillah.inventariskti.login.TableLogin

@Database(entities = [TableLogin::class], version = 1)
abstract class InventarisDB : RoomDatabase() {
    abstract fun loginDao(): DaoLogin

    companion object {
        private var INSTANCE: InventarisDB? = null

        fun getInstance(context: Context): InventarisDB? {
            if (INSTANCE == null) {
                synchronized(InventarisDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        InventarisDB::class.java,
                        "inventaris.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
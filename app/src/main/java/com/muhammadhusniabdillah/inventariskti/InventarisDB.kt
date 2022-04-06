package com.muhammadhusniabdillah.inventariskti

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.muhammadhusniabdillah.inventariskti.home.DaoHome
import com.muhammadhusniabdillah.inventariskti.home.TableHome
import com.muhammadhusniabdillah.inventariskti.login.DaoLogin
import com.muhammadhusniabdillah.inventariskti.login.TableLogin

@Database(
    entities = [TableLogin::class, TableHome::class],
    version = 2,
    exportSchema = false
)
abstract class InventarisDB : RoomDatabase() {
    abstract fun loginDao(): DaoLogin
    abstract fun homeDao(): DaoHome

    companion object {
        private var INSTANCE: InventarisDB? = null

        fun getInstance(context: Context): InventarisDB? {
            if (INSTANCE == null) {
                synchronized(InventarisDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        InventarisDB::class.java,
                        "inventaris.db"
                    ).addMigrations(migrateVersionOneToTwo()).build()
                }
            }
            return INSTANCE
        }

        private fun migrateVersionOneToTwo() : Migration {
            return object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE `TableHome` (" +
                                "`id` INTEGER , " +
                                "`barcode` TEXT NOT NULL, " +
                                "`spec` TEXT NOT NULL, " +
                                "`location` TEXT NOT NULL," +
                                "`pic` TEXT NOT NULL," +
                                "`condition` TEXT NOT NULL," +
                                "PRIMARY KEY(`id`))"
                    )
                }
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
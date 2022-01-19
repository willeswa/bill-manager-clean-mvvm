package app.monkpad.billmanager.data.local_data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryEntity

@Database(
    version = 1,
    entities = [CategoryEntity::class, BillEntity::class],
    exportSchema = false)
abstract class BillsManagerDatabase : RoomDatabase() {

    companion object {
        private lateinit var INSTANCE: BillsManagerDatabase

        fun getDatabase(context: Context): BillsManagerDatabase {
            synchronized(BillsManagerDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BillsManagerDatabase::class.java,
                        "bills_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun billDao(): BillDao

    abstract fun categoryDao(): CategoryDao
}
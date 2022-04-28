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
    exportSchema = true,
    autoMigrations = [
    AutoMigration(from = 1, to = 2)
])
abstract class BillsManagerDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "bills_database"
    }

    abstract fun billDao(): BillDao

    abstract fun categoryDao(): CategoryDao
}
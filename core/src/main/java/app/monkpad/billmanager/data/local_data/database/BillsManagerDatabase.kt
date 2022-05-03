package app.monkpad.billmanager.data.local_data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import app.monkpad.billmanager.data.local_data.models.BillEntity

@Database(
    version = 2,
    entities = [BillEntity::class],
    exportSchema = true,
    autoMigrations = [
    AutoMigration(from = 1, to = 2)
])
abstract class BillsManagerDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "bills_database"
    }

    abstract fun billDao(): BillDao
}
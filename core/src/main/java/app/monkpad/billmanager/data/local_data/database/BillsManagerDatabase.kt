package app.monkpad.billmanager.data.local_data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryEntity


@Database(
    entities = [CategoryEntity::class, BillEntity::class],
    version = 1,
    exportSchema = false)
abstract class BillsManagerDatabase: RoomDatabase(){
    abstract val billsDao: BillDao

    abstract val categoryDao: CategoryDao
}

private lateinit var INSTANCE: BillsManagerDatabase

fun getDatabase(context: Context){
    synchronized(BillsManagerDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                BillsManagerDatabase::class.java,
                "bills_database"
            ).build()
        }
    }
}
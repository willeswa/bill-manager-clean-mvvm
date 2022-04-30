package app.monkpad.billmanager.framework.di

import android.content.Context
import androidx.room.Room
import app.monkpad.billmanager.data.local_data.database.BillDao
import app.monkpad.billmanager.data.local_data.database.BillsManagerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideBillsDao(db: BillsManagerDatabase) = db.billDao()


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BillsManagerDatabase{
        return Room.databaseBuilder(
            context,
            BillsManagerDatabase::class.java,
            BillsManagerDatabase.DATABASE_NAME
        ).build()
    }
}
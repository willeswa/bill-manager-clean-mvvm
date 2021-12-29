package app.monkpad.billmanager.data.local_data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.monkpad.billmanager.data.local_data.models.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories_table")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(vararg categories: CategoryEntity)
}
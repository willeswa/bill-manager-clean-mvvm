package app.monkpad.billmanager.data.local_data.datasource

import android.content.Context
import app.monkpad.billmanager.data.local_data.database.BillsManagerDatabase
import app.monkpad.billmanager.data.local_data.database.CategoryDao
import app.monkpad.billmanager.data.mappers.asDomainModel
import app.monkpad.billmanager.data.mappers.asEntityModel
import app.monkpad.billmanager.domain.models.Category
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(@ApplicationContext context: Context) {

    @Inject lateinit var categoriesDao: CategoryDao

    suspend fun addCategory(category: Category) =
        categoriesDao.addCategory(category.asEntityModel())

    suspend fun getCategories(): Flow<List<Category>> =
        categoriesDao.getAllCategories().map{it.map{cat -> cat.asDomainModel()}}

}
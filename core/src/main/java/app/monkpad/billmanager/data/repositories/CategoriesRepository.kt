package app.monkpad.billmanager.data.repositories

import app.monkpad.billmanager.data.local_data.datasource.CategoryLocalDataSource
import app.monkpad.billmanager.data.mappers.asDomainModel
import app.monkpad.billmanager.domain.models.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoriesRepository(
    private val localDataSource: CategoryLocalDataSource) {


    suspend fun addCategory(category: Category) =
        localDataSource.addCategory(category)


    suspend fun getCategories(): Flow<List<Category>> =
        localDataSource.getCategories()
}
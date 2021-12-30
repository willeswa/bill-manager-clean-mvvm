package app.monkpad.billmanager.data.repositories

import app.monkpad.billmanager.data.local_data.datasource.CategoryLocalDataSource
import app.monkpad.billmanager.data.mappers.asDomainModel
import app.monkpad.billmanager.domain.models.Category

class CategoriesRepository(
    private val localDataSource: CategoryLocalDataSource) {


    suspend fun addCategory(category: Category) =
        localDataSource.addCategory(category)


    suspend fun getCategories(): List<Category> =
        localDataSource.getCategories().map{it.asDomainModel()}
}
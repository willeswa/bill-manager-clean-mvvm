package app.monkpad.billmanager.data.local_data.datasource

import android.content.Context
import app.monkpad.billmanager.data.local_data.database.BillsManagerDatabase
import app.monkpad.billmanager.data.mappers.asEntityModel
import app.monkpad.billmanager.domain.models.Category

class CategoryLocalDataSource(context: Context) {

    private val categoriesDao = BillsManagerDatabase
        .getDatabase(context.applicationContext)
        .categoryDao()

    suspend fun addCategory(category: Category) =
        categoriesDao.addCategory(category.asEntityModel())

    suspend fun getCategories() =
        categoriesDao.getAllCategories()

}
package app.monkpad.billmanager.domain.usecases

import app.monkpad.billmanager.data.repositories.CategoriesRepository
import app.monkpad.billmanager.domain.models.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val repository: CategoriesRepository) {

    suspend operator fun invoke(): Flow<List<Category>> =
        repository.getCategories()
}
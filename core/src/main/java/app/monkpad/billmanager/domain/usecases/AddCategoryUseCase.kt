package app.monkpad.billmanager.domain.usecases

import app.monkpad.billmanager.data.repositories.CategoriesRepository
import app.monkpad.billmanager.domain.models.Category

class AddCategoryUseCase(private val repository: CategoriesRepository) {

    suspend operator fun invoke(category: Category) =
            repository.addCategory(category)
}
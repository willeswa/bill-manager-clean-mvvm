package app.monkpad.billmanager.presentation.interactions

import app.monkpad.billmanager.framework.models.enums.Categories

class CategoryItemSelect(val clickListener: (category: Categories) -> Unit) {
    fun onCategorySelected(categories: Categories) = clickListener(categories)
}
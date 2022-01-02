package app.monkpad.billmanager.domain.models

data class Bill(
    val description: String,
    val amount: Float,
    val categoryName: String,
    val dueDate: Long,
    val repeat: Boolean,
    val settled: Boolean
)

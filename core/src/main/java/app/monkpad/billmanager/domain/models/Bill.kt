package app.monkpad.billmanager.domain.models

data class Bill(
    val id: Int,
    val description: String,
    val amount: Float,
    val categoryName: String,
    val dueDate: Long,
    val repeat: Int?,
    val settled: Boolean
)
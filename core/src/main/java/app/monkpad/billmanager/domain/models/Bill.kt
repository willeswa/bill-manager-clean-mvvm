package app.monkpad.billmanager.domain.models

data class Bill(
    val description: String,
    val amount: Float,
    val dueDate: String,
    val repeat: Boolean,
    val settled: Boolean
)

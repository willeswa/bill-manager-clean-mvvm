package app.monkpad.billmanager.domain

data class Bill(
    val description: String,
    val amount: Float,
    val dueDate: String,
    val repeat: Boolean,
    val settled: Boolean
)

package app.monkpad.billmanager.framework.models

data class BillDTO(
    val description: String,
    val amount: Float,
    val dueDate: Long,
    val categoryName: String,
    val repeat: Boolean = false,
    var paid: Boolean = false,
    val overdue: Boolean
)
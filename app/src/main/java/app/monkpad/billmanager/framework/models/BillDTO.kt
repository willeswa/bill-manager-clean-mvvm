package app.monkpad.billmanager.framework.models

data class BillDTO(
    val id: Int = -1,
    var description: String,
    var amount: Float,
    var dueDate: Long,
    var categoryName: String,
    var repeat: Boolean = false,
    var paid: Boolean = false,
    val overdue: Boolean
)
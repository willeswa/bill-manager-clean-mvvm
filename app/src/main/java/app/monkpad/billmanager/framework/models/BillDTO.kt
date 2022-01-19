package app.monkpad.billmanager.framework.models

data class BillDTO(
    val id: Int = -1,
    var description: String,
    var amount: Float,
    var dueDate: Long,
    var categoryName: String,
    var repeat: Long?,
    var paid: Boolean = false,
    val overdue: Boolean,
    var nextDueDate: Long?,
    var paidOn: Long?
)
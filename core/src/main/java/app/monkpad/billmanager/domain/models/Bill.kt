package app.monkpad.billmanager.domain.models

data class Bill(
    val id: Int,
    val description: String,
    val amount: Float,
    val categoryName: String,
    var dueDate: Long,
    val repeat: Long?,
    var settled: Boolean,
    val nextDueDate: Long?,
    val paidOn: Long?
)
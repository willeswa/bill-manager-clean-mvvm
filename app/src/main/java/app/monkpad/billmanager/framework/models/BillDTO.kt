package app.monkpad.billmanager.framework.models

data class BillDTO(
    val description: String,
    val amount: Float,
    val dueDate: Long,
    val categoryName: String,
    val categoryLogo: String,
    val repeat: Boolean = false,
    val paid: Boolean = false,
)
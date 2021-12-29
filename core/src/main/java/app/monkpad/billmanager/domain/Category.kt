package app.monkpad.billmanager.domain

data class Category(
    val name: String,
    val logo: String,
    val bills: List<Bill>
)

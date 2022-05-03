package app.monkpad.billmanager.framework.models.enums

import app.monkpad.billmanager.R

enum class Categories(val title: String, val color: Int, val drawable: Int) {
    HEALTH(
        "Health Care",
        R.color.health_care_pink,
        R.drawable.category_health),
    UTILITIES(
        "Utility",
        R.color.utility_orange,
        R.drawable.category_utility),
    ENTERTAINMENT(
        "Investment",
        R.color.investment_green,
        R.drawable.category_investment),
    EDUCATION(
        "Education",
        R.color.education_lilac,
        R.drawable.category_education
    ),
    FOOD(
       "Food",
        R.color.food_maize,
        R.drawable.category_food),
    PERSONAL(
       "Personal",
        R.color.personal_drab,
        R.drawable.category_personal),
    TRANSPORT(
       "Transport",
        R.color.transport,
        R.drawable.category_transport),
    DEBT(
       "Debt",
        R.color.debt,
        R.drawable.category_debt),
    HOUSING(
        "Housing",
        R.color.housing,
        R.drawable.category_housing),
    OTHERS(
        "Others",
        R.color.others,
        R.drawable.category_other)
}
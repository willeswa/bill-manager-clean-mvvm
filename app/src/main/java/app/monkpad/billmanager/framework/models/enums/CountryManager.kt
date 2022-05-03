package app.monkpad.billmanager.framework.models.enums

import java.util.*
import kotlin.collections.HashMap


class CountryManager {


    companion object {


        fun getCurrency(language: String, countryName: String): String {
            return Currency.getInstance(Locale(language, countryName)).symbol
        }

        fun getCurrencyName(language: String, countryName: String): String{
            return Currency.getInstance(Locale(language, countryName)).displayName
        }
    }



}
package com.example.crm.data

import androidx.room.TypeConverter
import com.example.crm.model.CustomerActions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * converter for java.util.Date
 */
class DataConverter {

    //convert customerAction list to string gson
    @TypeConverter
    fun fromCustomerActionsList(countryLang: List<CustomerActions>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type =
            object : TypeToken<List<CustomerActions>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    //convert from string gson to customerAction list
    @TypeConverter
    fun toCustomerActionsList(countryLangString: String?): List<CustomerActions>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type =
            object : TypeToken<List<CustomerActions>?>() {}.type
        return gson.fromJson(countryLangString, type)
    }
}
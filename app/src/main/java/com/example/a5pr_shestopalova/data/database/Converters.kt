package com.example.a5pr_shestopalova.data.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toString(value: Array<String>): String {
        return value.joinToString(separator = ", ")
    }
}


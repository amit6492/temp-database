package com.example.myapplication.databse

import com.example.myapplication.type.ColumnType


/**
 * Model class for Column format
 */
data class ColumnData(
    val name: String,
    val type: ColumnType
)

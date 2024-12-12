package com.example.myapplication.databse

import com.example.myapplication.exception.DatabaseException
import com.example.myapplication.type.ColumnType
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


/**
 * All the Table functionalities to insert row and column
 * Where condition (Its a normal filter function)
 */
class TableFunctions(private val tableName: String) {
    val databaseColumns = mutableListOf<ColumnData>()
    private val rows = mutableListOf<Map<String, Any?>>()
    private val lock = Mutex() //to ensure coroutine-safe access to shared resources in a concurrent environment.

    suspend fun addColumn(name: String, type: ColumnType){
        lock.withLock { //It ensures that a critical section of code is executed safely, with the lock acquired before execution and automatically released afterward
            if (databaseColumns.any { it.name == name }){
                throw DatabaseException("Column by the name $name already exist")
            }
            databaseColumns.add(
                ColumnData(
                name = name,
                type = type
                )
            )
        }
    }

    suspend fun insertRow(row: Map<String, Any?>){
        lock.withLock {
            val newRow = mutableMapOf<String, Any?>()
            for (column in databaseColumns) {
                val value = row[column.name] ?: throw DatabaseException("Column ${column.name} is missing")
                when(column.type){
                    ColumnType.STRING ->{
                        if (value !is String || value.length > 20){
                            throw DatabaseException("Please provide a correct input")
                        }
                    }
                    ColumnType.INT -> {
                        if (value !is Int || value !in -100..100){
                            throw DatabaseException("Please provide a correct input")
                        }
                    }
                }
                newRow[column.name] = value
            }
            println("Coroutine ${Thread.currentThread().name} is inserting a row.")
            delay(1000L)
            rows.add(newRow)
        }
    }

    suspend fun getRowData(){
        lock.withLock {
            if (rows.isEmpty()){
                println("Table $tableName is empty")
            }
            println("Table $tableName")
            println(databaseColumns.joinToString("\t"){it.name})
            rows.forEach { row ->
                println(databaseColumns.joinToString("\t") { row[it.name].toString() })
            }
        }
    }

    suspend fun whereQuery(predicate: (Map<String, Any?>) -> Boolean): List<Map<String, Any?>> {
        return lock.withLock {
            rows.filter(predicate)
        }
    }


}
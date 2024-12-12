package com.example.myapplication.databse

import com.example.myapplication.exception.DatabaseException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class OwnDatabase {
    private val tables = mutableMapOf<String, TableFunctions>()
    private val lock = Mutex()

    suspend fun createTable(name: String): TableFunctions {
        return lock.withLock { //It ensures that a critical section of code is executed safely, with the lock acquired before execution and automatically released afterward
            if (tables.containsKey(name)){
                throw DatabaseException("Table $name already exist")
            }
            val table = TableFunctions(name)
            tables[name] = table
            table
        }
    }

    suspend fun deleteTable(name: String){
        lock.withLock {
            if (tables.remove(name) == null){
                throw DatabaseException("Table $name does not exist!")
            }
            tables.clear()
        }
    }

    suspend fun getTable(name: String): TableFunctions {
        return lock.withLock {
            tables[name] ?: throw DatabaseException("Table $name does not exist")
        }
    }
}
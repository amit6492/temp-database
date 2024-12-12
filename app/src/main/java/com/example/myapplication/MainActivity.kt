package com.example.myapplication

import com.example.myapplication.databse.OwnDatabase
import com.example.myapplication.type.ColumnType

import kotlinx.coroutines.runBlocking
import java.util.Scanner

fun main(){
    runBlocking {
        val newDatabase = OwnDatabase()
        val scanner = Scanner(System.`in`)

        println(
            "Welcome to the Database Simulation! " +
                    "(Not a real Database, just a test implementation)"
        )
        while (true) {
            println("\nChoose an option:")
            println("1. Create Table")
            println("2. Add Column to the Table")
            println("3. Insert Row into the Table")
            println("4. Show Table")
            println("5. WHERE QUERY")
            println("6. Delete Table")
            println("7. Exit")

            when (scanner.nextLine().trim()) {
                "1" -> {
                    print("Enter table name: ")
                    val tableName = scanner.nextLine().trim()
                    try {
                        newDatabase.createTable(tableName)
                        println("Table $tableName created successfully.")
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }

                "2" -> {
                    print("Enter table name: ")
                    val tableName = scanner.nextLine().trim()
                    print("Enter column name: ")
                    val columnName = scanner.nextLine().trim()
                    print("Enter column type (STRING/INT): ")
                    val columnType = scanner.nextLine().trim().uppercase()
                    try {
                        val table = newDatabase.getTable(tableName)
                        table.addColumn(columnName, ColumnType.valueOf(columnType))
                        println("Cool! Column $columnName added to table $tableName.")
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }

                "3" -> {
                    print("Enter table name: ")
                    val tableName = scanner.nextLine().trim()
                    val table = try {
                        newDatabase.getTable(tableName)
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                        null
                    }
                    if (table != null) {
                        println("Enter row data as key=value pairs" +
                                " (e.g., Name=AMIT press enter AGE=31). Enter blank to finish.")
                        val row = mutableMapOf<String, Any?>()
                        while (true) {
                            val input = scanner.nextLine().trim()
                            if (input.isBlank()) break

                            val keyValue = input.split("=", limit = 2)
                            if (keyValue.size != 2) {
                                println("Invalid input. Use key=value format.")
                                continue
                            }

                            val (key, value) = keyValue.map { it.trim() }
                            val column = table.databaseColumns.find { it.name == key }

                            if (column == null) {
                                println("Error: Column $key does not exist in table $tableName.")
                                continue
                            }

                            try {
                                when (column.type) {
                                    ColumnType.STRING -> row[key] = value
                                    ColumnType.INT -> row[key] = value.toInt()
                                }
                            } catch (e: Exception) {
                                println("Error: Value for column $key must be of type ${column.type}.")
                            }
                        }

                        try {
                            table.insertRow(row)
                            println("Row inserted into table $tableName.")
                        } catch (e: Exception) {
                            println("Error: ${e.message}")
                        }
                    }
                }

                "4" -> {
                    print("Enter table name: ")
                    val tableName = scanner.nextLine().trim()
                    try {
                        newDatabase.getTable(tableName).getRowData()
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }

                "5" -> {
                    print("Enter table name: ")
                    val tableName = scanner.nextLine().trim()
                    print("Enter filter condition (e.g., Age > 25): ")
                    val condition = scanner.nextLine().trim()
                    try {
                        val table = newDatabase.getTable(tableName)
                        val filteredRows = table.whereQuery { row ->
                            condition.split(" ").let {
                                val key = it[0]
                                val operator = it[1]
                                val value = it[2]
                                when (operator) {
                                    ">" -> (row[key] as? Int ?: 0) > value.toInt()
                                    "<" -> (row[key] as? Int ?: 0) < value.toInt()
                                    ">=" -> (row[key] as? Int ?: 0) >= value.toInt()
                                    "<=" -> (row[key] as? Int ?: 0) < value.toInt()
                                    "=" -> row[key].toString() == value
                                    else -> false
                                }
                            }
                        }
                        println("Filtered rows: $filteredRows")
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }

                "6" -> {
                    print("Enter table name: ")
                    val tableName = scanner.nextLine().trim()
                    try {
                        newDatabase.deleteTable(tableName)
                        println("Table $tableName deleted.")
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }
                "7" -> {
                    print("Adios Amigos!")
                    break;
                }

                else -> println("Invalid option. Please try again.")
            }
        }
//        db.createTable("users").apply {
//            addColumn("name", ColumnType.STRING)
//            addColumn("age", ColumnType.INT)
//        }

//        val table = db.getTable("users")
//
//        val jobs = List(5) { id ->
//            delay(2000L)
//            launch {
//                try {
//                    table.insertRow(mapOf("name" to "User$id", "age" to id * 10))
//                    println("Coroutine $id: Inserted row successfully")
//                } catch (e: Exception) {
//                    println("Coroutine $id: Error - ${e.message}")
//                }
//            }
//        }
//
//        jobs.forEach { it.join() }
//
//        println("\nFinal Table Data:")
//        table.getRowData()
    }
}
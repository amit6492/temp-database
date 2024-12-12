# Temp Database  
Implemented in Kotlin, Just a temporary Database  

# Features  
Database Operations:  

1. Create and delete tables.
2. Define columns with types (STRING or INT)
3. Insert, show, and Filter(Where) rows.
4. Concurrency Control: Mutex (Already present in coroutine)
5. Ensures thread-safe access to shared resources using Mutex.


# Validation:    
Columns of type STRING must have a length ≤ 20.  
Columns of type INT must have values in the range [-100, 100].  
Required columns must be provided during row insertion.  

# How to run the database   
Go to MainActivity Class and run the main function (Note - Dont run the whole Application)

# When you run the program, you’ll see the following options:  
1. Create Table  
2. Add Column to Table  
3. Insert Row into Table  
4. Show Table  
5. WHERE filter   
6. Delete Table  
7. Exit  


# Examples  
Create a Table  
Choose an option: 1  
Enter table name: users  
Table users created successfully. 

Add Columns  
Choose an option: 2  
Enter table name: users  
Enter column name: name  
Enter column type (STRING/INT): STRING  
Column name added to table users. 

Choose an option: 2  
Enter table name: users  
Enter column name: age  
Enter column type (STRING/INT): INT  
Column age added to table users.  

Insert Rows    
Choose an option: 3  
Enter table name: users  
Enter row data as key=value pairs (e.g., name=Amit "press enter" age=32). Enter blank to finish.  
name=Amit  
age=32  
Row inserted into table users.  

Show Table  
Choose an option: 4  
Enter table name: users  
Table: users  
name    age  
Amit   32  

Filter(WHERE) Rows  
Choose an option: 5  
Enter table name: users  
Enter WHERE condition (e.g., age > 20): age > 20  
Filtered rows: [{name=Amit, age=32}]   

Choose an option: 6  
Existing tables: users  
Delete Table  


# Thread Safety:  
The project uses kotlinx.coroutines.sync.Mutex to ensure concurrent operations on tables and the database are thread-safe.
Critical sections are wrapped in withLock.

# What more can we do   
1. We can add Airthmetic Operations
2. We can put a fitler for contains
3. Apply operations on the Complete Columns
4. Delete Rows

# Author
Created by Amit6492. 😊

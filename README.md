 **TaskManager Java Project with MySQL integration**. 

---
# Smart Task Manager - Java Swing Project

**Smart Task Manager** is a Java-based desktop application developed using **Java Swing** that allows users to manage tasks efficiently. It supports **adding, editing, completing, deleting tasks**, and stores all tasks in a **MySQL database**. This project is ideal for learning **Java GUI, file handling, MySQL integration, and OOP concepts**.

---

## **Table of Contents**

- [Features](#features)  
- [Screenshots](#screenshots)  
- [Installation](#installation)  
- [Database Setup](#database-setup)  
- [Usage](#usage)  
- [Project Structure](#project-structure)  
- [Technologies Used](#technologies-used)  
- [License](#license)  

---

## **Features**

- Add new tasks with title, description, priority, and due date.  
- Edit existing tasks.  
- Mark tasks as complete.  
- Delete tasks from the list.  
- Track overdue tasks.  
- All data is stored in **MySQL** for persistence.  
- Search and filter tasks using the dashboard search bar.  
- Task statistics: Total, Completed, Overdue, Progress Bar.

---



## **Installation**

1. **Clone the repository:**

```bash
git clone https://github.com/username/TaskManagerJava.git
cd TaskManagerJava
````

2. **Import into Eclipse:**

   * Open Eclipse → File → Import → Existing Projects into Workspace → Select your project folder.

3. **Add MySQL JDBC Driver to Build Path:**

   * Download the [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
   * Right-click project → Build Path → Configure Build Path → Libraries → Add External JAR → Select connector jar

4. **Update DB Connection:**

   * Open `project3/DBConnection.java`
   * Replace `USER` and `PASSWORD` with your MySQL credentials.

---

## **Database Setup**

1. Open **MySQL Workbench** or terminal.
2. Create database:

```sql
CREATE DATABASE task_manager_db;
```

3. Use the database:

```sql
USE task_manager_db;
```

4. Create tasks table:

```sql
CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority VARCHAR(50),
    due_date DATE,
    status VARCHAR(50)
);
```

---

## **Usage**

1. Run the `WelcomePage.java` in Eclipse.
2. Click **ENTER DASHBOARD** to open the main Task Manager.
3. Use the **sidebar buttons** to add, edit, complete, or delete tasks.
4. All changes are saved automatically to the MySQL database.

---

## **Project Structure**

```
TaskManagerJava/
│
├─ src/project3/
│   ├─ Task.java
│   ├─ TaskManagerUI.java
│   ├─ WelcomePage.java
│   └─ DBConnection.java
│
├─ .gitignore
├─ README.md
└─ lib/  (Optional: MySQL Connector JAR)
```

---

## **Technologies Used**

* Java SE 17+
* Java Swing (GUI)
* MySQL / MySQL Workbench
* JDBC
* Eclipse IDE

---






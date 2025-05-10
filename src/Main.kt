import java.io.File
import java.util.Scanner

data class Task(val id: Int, val description: String, var isComplete: Boolean)

fun main() {
    val tasks = mutableListOf<Task>()
    val file = File("tasks.txt")
    val scanner = Scanner(System.`in`)

    // Load tasks from file
    loadTasks(file, tasks)

    // Main menu
    while (true) {
        println("Task Manager")
        println("1. Add Task")
        println("2. View Tasks")
        println("3. Mark Task Complete")
        println("4. Delete Task")
        println("5. Exit")
        print("Select an option: ")

        when (scanner.nextInt()) {
            1 -> addTask(tasks, file)
            2 -> viewTasks(tasks)
            3 -> markTaskComplete(tasks, file)
            4 -> deleteTask(tasks, file)
            5 -> {
                saveTasks(file, tasks)
                println("Exiting program.")
                break
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}

// Function to add a task
fun addTask(tasks: MutableList<Task>, file: File) {
    val scanner = Scanner(System.`in`)
    println("Enter task description: ")
    val description = scanner.nextLine()
    val id = if (tasks.isEmpty()) 1 else tasks.maxOf { it.id } + 1
    val task = Task(id, description, false)
    tasks.add(task)
    saveTasks(file, tasks)
    println("Task added successfully.")
}

// Function to view tasks
fun viewTasks(tasks: MutableList<Task>) {
    if (tasks.isEmpty()) {
        println("No tasks available.")
    } else {
        println("Tasks:")
        tasks.forEach { task ->
            val status = if (task.isComplete) "Completed" else "Incomplete"
            println("${task.id}. ${task.description} - $status")
        }
    }
}

// Function to mark a task as complete
fun markTaskComplete(tasks: MutableList<Task>, file: File) {
    val scanner = Scanner(System.`in`)
    println("Enter task ID to mark as complete: ")
    val taskId = scanner.nextInt()
    val task = tasks.find { it.id == taskId }

    if (task != null && !task.isComplete) {
        task.isComplete = true
        saveTasks(file, tasks)
        println("Task marked as complete.")
    } else {
        println("Task not found or already completed.")
    }
}

// Function to delete a task
fun deleteTask(tasks: MutableList<Task>, file: File) {
    val scanner = Scanner(System.`in`)
    println("Enter task ID to delete: ")
    val taskId = scanner.nextInt()
    val task = tasks.find { it.id == taskId }

    if (task != null) {
        tasks.remove(task)
        saveTasks(file, tasks)
        println("Task deleted successfully.")
    } else {
        println("Task not found.")
    }
}

// Function to load tasks from a file
fun loadTasks(file: File, tasks: MutableList<Task>) {
    if (file.exists()) {
        file.forEachLine {
            val parts = it.split(", ")
            if (parts.size == 3) {
                val id = parts[0].toInt()
                val description = parts[1]
                val isComplete = parts[2].toBoolean()
                tasks.add(Task(id, description, isComplete))
            }
        }
    }
}

// Function to save tasks to a file
fun saveTasks(file: File, tasks: MutableList<Task>) {
    file.printWriter().use { out ->
        tasks.forEach { task ->
            out.println("${task.id}, ${task.description}, ${task.isComplete}")
        }
    }
}

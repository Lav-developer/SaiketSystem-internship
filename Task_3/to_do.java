package Task_3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class to_do {
    private String title;
    private boolean isComplete;

    public to_do(String title) {
        this.title = title;
        this.isComplete = false; 
    }

    public String getTitle() {
        return title;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void markComplete() {
        this.isComplete = true;
    }

    @Override
    public String toString() {
        String status = isComplete ? "[✓]" : "[ ]";
        return status + " " + title;
    }
}

class TodoListApp {
    private ArrayList<to_do> tasks;
    private Scanner scanner;

    public TodoListApp() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    private void addTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Task title cannot be empty.");
            return;
        }
        to_do newTask = new to_do(title);
        tasks.add(newTask);
        System.out.println("Task added successfully.");
    }

    private void markTaskComplete() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to mark. Please add a task first.");
            return;
        }
        displayTasks(); 
        System.out.print("Enter the task number to mark as complete: ");
        try {
            int index = scanner.nextInt();
            scanner.nextLine();
            if (index >= 1 && index <= tasks.size()) {
                to_do task = tasks.get(index - 1);
                if (task.isComplete()) {
                    System.out.println("Task \"" + task.getTitle() + "\" is already complete.");
                } else {
                    task.markComplete();
                    System.out.println("Task \"" + task.getTitle() + "\" marked as complete.");
                }
            } else {
                System.out.println("Invalid task number. Please enter a number between 1 and " + tasks.size());
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); 
        }
    }

    private void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your to-do list is empty.");
        } else {
            System.out.println("\n===== To-Do List =====");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i).toString());
            }
            System.out.println("=====================\n");
        }
    }

    public void run() {
        int choice = 0;
        System.out.println("=== Welcome to the To-Do List Application ===\n");

        do {
            System.out.println("Please choose an option:");
            System.out.println("1. Add a task");
            System.out.println("2. Mark a task as complete");
            System.out.println("3. Display all tasks");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 4.");
                scanner.nextLine(); 
                continue;
            }

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    markTaskComplete();
                    break;
                case 3:
                    displayTasks();
                    break;
                case 4:
                    System.out.println("Thank you for using the To-Do List App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    public static void main(String[] args) {
        TodoListApp app = new TodoListApp();
        app.run();
    }
}
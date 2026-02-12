package blitz.ui;

import java.util.Scanner;
import blitz.model.Task;
import blitz.model.TaskList;
import java.util.ArrayList;

/**
 * Responsible for user interaction (printing lines, welcome, errors, and reading input).
 *
 * <p>All display-related logic should be kept here to separate concerns.
 */
public class Ui {
    /** Divider line used in CLI messages. */
    private static final String LINE = "____________________________________________________________";

    /** Print the welcome header to the console. */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Blitz");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Reads a trimmed command line from the provided scanner.
     *
     * @param scanner the scanner to read from (usually System.in wrapped scanner)
     * @return the trimmed input string from the user
     */
    public String readCommand(final Scanner scanner) {
        return scanner.nextLine().trim();
    }

    /** Print the divider line. */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Show an error that occurred during loading of storage.
     *
     * @param message the error message to present
     */
    public void showLoadingError(final String message) {
        System.out.println(LINE);
        System.out.println(" Cannot load tasks: " + message);
        System.out.println(LINE);
    }

    /**
     * Print a short inline error message (used for command parse/validation errors).
     *
     * @param message the error message text
     */
    public void showError(final String message) {
        System.out.println(" " + message);
    }

    /**
     * Display the list of tasks.
     *
     * @param tasks the TaskList to present
     */
    public void showList(final TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        if (tasks.size() == 0) {
            System.out.println("Currently no task ongoing");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Show that a task has been added.
     *
     * @param task the added task
     * @param size the new size of the list
     */
    public void showAdded(final Task task, final int size) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " tasks in the list.");
    }

    /** Show that a task has been removed. */
    public void showRemoved(final Task task, final int size) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " tasks in the list.");
    }

    /** Show that a task has been marked done. */
    public void showMarked(final Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /** Show that a task has been unmarked. */
    public void showUnmarked(final Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }

    /** Show the list of matching tasks. */
    public void showFound(ArrayList<Task> matches) {
        System.out.println(" Here are the matching tasks in your list:");
        if (matches.isEmpty()) {
            System.out.println(" No matching tasks found.");
            return;
        }
        for (int i = 0; i < matches.size(); i++) {
            System.out.println(" " + (i + 1) + "." + matches.get(i));
        }
    }
}

package blitz;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.model.*;

/**
 * Application entry point for Blitz.
 *
 * <p>Holds the main loop and delegates parsing / persistence to Parser and Storage.
 */
public class Blitz {
    /** CLI divider line shown between messages. */
    public static final String LINE = "____________________________________________________________";

    /**
     * Application main method.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = Storage.loadTasks();
        } catch (IOException e) {
            System.out.println("Cannot load tasks: " + e.getMessage());
        }

        System.out.println(LINE);
        System.out.println(" Hello! I'm Blitz");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);

        while (true) {
            final String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            if (input.equals("nothing")) {
                System.out.println(LINE);
                System.out.println("Then i can finally enjoy some break!");
                System.out.println(LINE);
                continue;
            }

            try {
                handleInput(input, tasks);
                Storage.saveTasks(tasks);
            } catch (BlitzException e) {
                System.out.println(LINE);
                System.out.println(" " + e.getMessage());
                System.out.println(LINE);
            } catch (IOException e) {
                System.out.println("Cannot save tasks: " + e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Processes a single user input command and mutates the task list.
     *
     * @param input raw user input
     * @param tasks list of tasks to update
     * @throws BlitzException when input is invalid or malformed
     */
    private static void handleInput(final String input, final ArrayList<Task> tasks) throws BlitzException {
        if (input.equals("list")) {
            System.out.println(LINE);
            System.out.println("Here are the tasks in your list:");

            if (tasks.isEmpty()) {
                System.out.println("Currently no task ongoing");
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(i + 1 + ". " + tasks.get(i));
                }
            }

            System.out.println(LINE);
            return;
        }

        if (input.startsWith("todo")) {
            final String descrip = input.substring(4).trim();
            if (descrip.isEmpty()) {
                throw new BlitzException("What is the todo description? Give me more details!");
            }

            final Task newTask = new Todo(descrip);
            tasks.add(newTask);
            System.out.println(LINE);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + newTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("deadline")) {
            final String restOfString = input.substring(8).trim();
            if (restOfString.isEmpty()) {
                throw new BlitzException("What is the deadline? I need a deadline!");
            }
            final int byIndex = restOfString.indexOf("/by");
            if (byIndex == -1) {
                throw new BlitzException("Deadline must have a /by!");
            }
            final String descrip = restOfString.substring(0, byIndex).trim();
            if (descrip.isEmpty()) {
                throw new BlitzException("What is the activity being due? Give me the description!");
            }
            final String by = restOfString.substring(byIndex + 3).trim();
            if (by.isEmpty()) {
                throw new BlitzException("By when? When is it due?");
            }
            final Task newTask = new Deadline(descrip, by);
            tasks.add(newTask);
            System.out.println(LINE);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + newTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("event")) {
            final String restOfString = input.substring(5).trim();
            if (restOfString.isEmpty()) {
                throw new BlitzException("Event details cannot be empty? I need more information!");
            }
            final int indexFrom = restOfString.indexOf("/from");
            final int indexTo = restOfString.indexOf("/to");
            if (indexFrom == -1 || indexTo == -1 || indexTo < indexFrom) {
                throw new BlitzException("What is the start and end details of this event?");
            }
            final String descrip = restOfString.substring(0, indexFrom).trim();
            if (descrip.isEmpty()) {
                throw new BlitzException("What is the event title or name? Give me the description!");
            }
            final String startTime = restOfString.substring(indexFrom + 5, indexTo).trim();
            final String endTime = restOfString.substring(indexTo + 3).trim();
            if (startTime.isEmpty() || endTime.isEmpty()) {
                throw new BlitzException("Event must have both start and end time!");
            }
            final Task newTask = new Event(descrip, startTime, endTime);
            tasks.add(newTask);
            System.out.println(LINE);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + newTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("mark")) {
            final String markNumber = input.substring(4).trim();
            if (markNumber.isEmpty()) {
                throw new BlitzException("How do i mark something that is not there? I need to mark something!");
            }
            final int taskNumber = Integer.parseInt(markNumber);
            final int index = taskNumber - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new BlitzException("Invalid task number");
            } else {
                tasks.get(index).markAsDone();
                System.out.println(LINE);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks.get(index));
                System.out.println(LINE);
            }
            return;
        }

        if (input.startsWith("unmark")) {
            final String markNumber = input.substring(6).trim();
            if (markNumber.isEmpty()) {
                throw new BlitzException("How do i unmark something that is not there? I need to unmark something!");
            }
            final int taskNumber = Integer.parseInt(markNumber);
            final int index = taskNumber - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new BlitzException("Invalid task number");
            } else {
                tasks.get(index).markNotDone();
                System.out.println(LINE);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks.get(index));
                System.out.println(LINE);
                return;
            }
        }

        if (input.startsWith("delete")) {
            final String restOfString = input.substring(6).trim();
            if (restOfString.isEmpty()) {
                throw new BlitzException("Delete what? Provide task number!");
            }
            final int taskNumber = Integer.parseInt(restOfString);
            final int index = taskNumber - 1;
            final Task delete = tasks.remove(index);
            System.out.println(LINE);
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + delete);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }
        throw new BlitzException("What is that? Try todo / deadline / event / mark / unmark / list/ bye");
    }
}

package blitz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import blitz.model.Task;
import blitz.model.Todo;
import blitz.model.Deadline;
import blitz.model.Event;
import blitz.storage.Storage;

/**
 * Entry point for the Blitz CLI application.
 *
 * <p>Keeps the original simple loop: read commands, mutate an in-memory list,
 * and persist to storage.
 */
public final class Blitz {
    /** Divider line printed between messages. */
    public static final String LINE = "____________________________________________________________";

    private Blitz() {
        // Utility class: prevent instantiation.
    }

    /**
     * Application main.
     *
     * @param args command-line args (unused)
     */
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

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
     * Handle a single user command and mutate the provided task list.
     *
     * @param input raw input line from user
     * @param tasks list of tasks to update
     * @throws BlitzException when the input is invalid
     */
    private static void handleInput(final String input, final List<Task> tasks) throws BlitzException {
        if (input.equals("list")) {
            System.out.println(LINE);
            System.out.println("Here are the tasks in your list:");
            if (tasks.isEmpty()) {
                System.out.println("Currently no task ongoing");
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            }
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("todo")) {
            final String desc = input.substring(4).trim();
            if (desc.isEmpty()) {
                throw new BlitzException("What is the todo description? Give me more details!");
            }
            final Task newTask = new Todo(desc);
            tasks.add(newTask);
            System.out.println(LINE);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + newTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("deadline")) {
            final String rest = input.substring(8).trim();
            if (rest.isEmpty()) {
                throw new BlitzException("What is the deadline? I need a deadline!");
            }
            final int byIdx = rest.indexOf("/by");
            if (byIdx == -1) {
                throw new BlitzException("Deadline must have a /by!");
            }
            final String desc = rest.substring(0, byIdx).trim();
            if (desc.isEmpty()) {
                throw new BlitzException("What is the activity being due? Give me the description!");
            }
            final String by = rest.substring(byIdx + 3).trim();
            if (by.isEmpty()) {
                throw new BlitzException("By when? When is it due?");
            }
            final Task newTask = new Deadline(desc, by);
            tasks.add(newTask);
            System.out.println(LINE);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + newTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("event")) {
            final String rest = input.substring(5).trim();
            if (rest.isEmpty()) {
                throw new BlitzException("Event details cannot be empty? I need more information!");
            }
            final int fromIdx = rest.indexOf("/from");
            final int toIdx = rest.indexOf("/to");
            if (fromIdx == -1 || toIdx == -1 || toIdx < fromIdx) {
                throw new BlitzException("What is the start and end details of this event?");
            }
            final String desc = rest.substring(0, fromIdx).trim();
            if (desc.isEmpty()) {
                throw new BlitzException("What is the event title or name? Give me the description!");
            }
            final String startTime = rest.substring(fromIdx + 5, toIdx).trim();
            final String endTime = rest.substring(toIdx + 3).trim();
            if (startTime.isEmpty() || endTime.isEmpty()) {
                throw new BlitzException("Event must have both start and end time!");
            }
            final Task newTask = new Event(desc, startTime, endTime);
            tasks.add(newTask);
            System.out.println(LINE);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + newTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("mark")) {
            final String num = input.substring(4).trim();
            if (num.isEmpty()) {
                throw new BlitzException("How do i mark something that is not there? I need to mark something!");
            }
            final int taskNumber = Integer.parseInt(num);
            final int index = taskNumber - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new BlitzException("Invalid task number");
            }
            tasks.get(index).markAsDone();
            System.out.println(LINE);
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(index));
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("unmark")) {
            final String num = input.substring(6).trim();
            if (num.isEmpty()) {
                throw new BlitzException("How do i unmark something that is not there? I need to unmark something!");
            }
            final int taskNumber = Integer.parseInt(num);
            final int index = taskNumber - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new BlitzException("Invalid task number");
            }
            tasks.get(index).markNotDone();
            System.out.println(LINE);
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks.get(index));
            System.out.println(LINE);
            return;
        }

        if (input.startsWith("delete")) {
            final String rest = input.substring(6).trim();
            if (rest.isEmpty()) {
                throw new BlitzException("Delete what? Provide task number!");
            }
            final int taskNumber = Integer.parseInt(rest);
            final int index = taskNumber - 1;
            final Task removed = tasks.remove(index);
            System.out.println(LINE);
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }

        throw new BlitzException("What is that? Try todo / deadline / event / mark / unmark / list/ bye");
    }
}

package blitz;

import java.io.IOException;
import java.util.ArrayList;
import blitz.command.Command;
import blitz.model.TaskList;
import blitz.model.Event;
import blitz.model.Deadline;
import blitz.model.Todo;
import blitz.model.Task;
import blitz.parser.Parser;
import blitz.storage.Storage;
import blitz.ui.GuiUi;
import blitz.BlitzException;

public class Blitz {
    private final TaskList tasks;
    private final GuiUi ui;
    private String commandType;

    public Blitz() {
        ui = new GuiUi();
        tasks = loadTasks();
    }

    // Refactor: Modularize task loading with assertions
    private TaskList loadTasks() {
        TaskList loaded;
        try {
            loaded = new TaskList(Storage.loadTasks());
        } catch (IOException e) {
            loaded = new TaskList();
        }
        assert loaded != null : "Task list should not be null!";
        return loaded;
    }

    public String getResponse(String input) {
        ui.clear();
        try {
            Command c = Parser.parse(input);
            assert c != null : "Parsed command should not be null!";  // Assertion for parsed command

            c.execute(tasks, ui);
            commandType = c.getClass().getSimpleName();

            // Assertions for task list integrity before saving
            assert tasks.asList() != null : "Tasks list should not be null when saving!";
            Storage.saveTasks(tasks.asList());

            return ui.getLastOutput().trim();
        } catch (BlitzException e) {
            commandType = "Error";
            ui.showError(e.getMessage());
            return ui.getLastOutput().trim();
        } catch (IOException e) {
            commandType = "Error";
            return "Cannot save tasks: " + e.getMessage();
        }
    }

    public String getCommandType() {
        return commandType;
    }

    // Refactor: Helper method to handle tasks
    private void handleTaskInput(String input) throws BlitzException {
        if (input.startsWith("todo")) {
            handleTodoInput(input);
        } else if (input.startsWith("deadline")) {
            handleDeadlineInput(input);
        } else if (input.startsWith("event")) {
            handleEventInput(input);
        } else {
            throw new BlitzException("Unknown command: " + input);
        }
    }

    // Refactor: Task-specific handling into separate methods for readability
    private void handleTodoInput(String input) throws BlitzException {
        String desc = input.substring(4).trim();
        validateDescription(desc, "What is the todo description? Give me more details!");
        Task newTask = new Todo(desc);
        tasks.add(newTask);
        ui.showAdded(newTask, tasks.size());
    }

    private void handleDeadlineInput(String input) throws BlitzException {
        String rest = input.substring(8).trim();
        validateDescription(rest, "What is the deadline? I need a deadline!");
        int byIndex = rest.indexOf("/by");
        if (byIndex == -1) throw new BlitzException("Deadline must have a /by!");
        String descrip = rest.substring(0, byIndex).trim();
        String by = rest.substring(byIndex + 3).trim();
        validateDescription(descrip, "What is the activity being due? Give me the description!");
        validateDescription(by, "By when? When is it due?");
        Task newTask = new Deadline(descrip, by);
        tasks.add(newTask);
        ui.showAdded(newTask, tasks.size());
    }

    private void handleEventInput(String input) throws BlitzException {
        String rest = input.substring(5).trim();
        int fromIndex = rest.indexOf("/from");
        int toIndex = rest.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new BlitzException("What is the start and end details of this event?");
        }
        String descrip = rest.substring(0, fromIndex).trim();
        String from = rest.substring(fromIndex + 5, toIndex).trim();
        String to = rest.substring(toIndex + 3).trim();
        validateDescription(descrip, "What is the event title or name? Give me the description!");
        validateDescription(from, "Event must have a start time!");
        validateDescription(to, "Event must have an end time!");
        Task newTask = new Event(descrip, from, to);
        tasks.add(newTask);
        ui.showAdded(newTask, tasks.size());
    }

    // Refactor: Centralized description validation
    private void validateDescription(String desc, String errorMessage) throws BlitzException {
        if (desc.isEmpty()) {
            throw new BlitzException(errorMessage);
        }
    }
}

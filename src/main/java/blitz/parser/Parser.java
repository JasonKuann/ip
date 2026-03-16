package blitz.parser;

import blitz.command.Command;
import blitz.command.ExitCommand;
import blitz.command.ListCommand;
import blitz.command.AddTodoCommand;
import blitz.command.AddDeadlineCommand;
import blitz.command.AddEventCommand;
import blitz.command.MarkCommand;
import blitz.command.UnmarkCommand;
import blitz.command.DeleteCommand;
import blitz.command.FindCommand;
import blitz.model.Task;
import blitz.model.Todo;
import blitz.model.Deadline;
import blitz.model.Event;
import blitz.model.TaskList;
import blitz.BlitzException;
import java.time.temporal.ChronoUnit;

/**
 * Parses raw user input into Command objects (or throws a BlitzException for invalid input).
 *
 * <p>This keeps parsing logic separate from the UI and application logic.
 */
public class Parser {

    /**
     * Parse a full raw input string into a {@link blitz.command.Command}.
     *
     * @param input raw input string
     * @return a Command representing the action to perform
     * @throws BlitzException when the input is invalid or missing required parts
     */
    public static Command parse(final String input) throws BlitzException {
        if (input.equals("bye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }
        if (input.startsWith("todo")) {
            return parseTodo(input);
        }
        if (input.startsWith("deadline")) {
            return parseDeadline(input);
        }
        if (input.startsWith("event")) {
            return parseEvent(input);
        }
        if (input.startsWith("mark")) {
            return parseMark(input);
        }
        if (input.startsWith("unmark")) {
            return parseUnmark(input);
        }
        if (input.startsWith("delete")) {
            return parseDelete(input);
        }
        if (input.startsWith("find")) {
            return parseFind(input);
        }
    
        throw new BlitzException("What is that? Try todo / deadline / event / mark / unmark / list/ bye / find");
    }
    
    private static Command parseTodo(final String input) throws BlitzException {
        final String desc = input.substring(4).trim();
        assert !desc.isEmpty() : "Todo description should not be empty!";
        if (desc.isEmpty()) {
            throw new BlitzException("What is the todo description? Give me more details!");
        }
        return new AddTodoCommand(desc);
    }
    
    private static Command parseDeadline(final String input) throws BlitzException {
        final String rest = input.substring(8).trim();
        assert !rest.isEmpty() : "Deadline details should not be empty!";
        if (rest.isEmpty()) {
            throw new BlitzException("What is the deadline? I need a deadline!");
        }
    
        final int byIndex = rest.indexOf("/by");
        if (byIndex == -1) {
            throw new BlitzException("Deadline must have a /by!");
        }
    
        final String descrip = rest.substring(0, byIndex).trim();
        final String by = rest.substring(byIndex + 3).trim();
    
        assert !descrip.isEmpty() : "Deadline description should not be empty!";
        assert !by.isEmpty() : "Deadline 'by' date should not be empty!";
    
        if (descrip.isEmpty()) {
            throw new BlitzException("What is the activity being due? Give me the description!");
        }
        if (by.isEmpty()) {
            throw new BlitzException("By when? When is it due?");
        }
    
        return new AddDeadlineCommand(descrip, by);
    }
    
    private static Command parseEvent(final String input) throws BlitzException {
        final String rest = input.substring(5).trim();
        assert !rest.isEmpty() : "Event details should not be empty!";
    
        final int fromIndex = rest.indexOf("/from");
        final int toIndex = rest.indexOf("/to");
    
        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new BlitzException("What is the start and end details of this event?");
        }
    
        final String descrip = rest.substring(0, fromIndex).trim();
        final String from = rest.substring(fromIndex + 5, toIndex).trim();
        final String to = rest.substring(toIndex + 3).trim();
    
        assert !descrip.isEmpty() : "Event description should not be empty!";
        assert !from.isEmpty() : "Event start time should not be empty!";
        assert !to.isEmpty() : "Event end time should not be empty!";
    
        if (descrip.isEmpty()) {
            throw new BlitzException("What is the event title or name? Give me the description!");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new BlitzException("Event must have both start and end time!");
        }
    
        return new AddEventCommand(descrip, from, to);
    }
    
    private static Command parseMark(final String input) throws BlitzException {
        final String num = input.substring(4).trim();
        assert !num.isEmpty() : "Task number for marking should not be empty!";
        if (num.isEmpty()) {
            throw new BlitzException("How do i mark something that is not there? I need to mark something!");
        }
    
        final int idx = Integer.parseInt(num) - 1;
        assert idx >= 0 : "Invalid task number!";
        return new MarkCommand(idx);
    }
    
    private static Command parseUnmark(final String input) throws BlitzException {
        final String num = input.substring(6).trim();
        assert !num.isEmpty() : "Task number for unmarking should not be empty!";
        if (num.isEmpty()) {
            throw new BlitzException("How do i unmark something that is not there? I need to unmark something!");
        }
    
        final int idx = Integer.parseInt(num) - 1;
        assert idx >= 0 : "Invalid task number!";
        return new UnmarkCommand(idx);
    }
    
    private static Command parseDelete(final String input) throws BlitzException {
        final String num = input.substring(6).trim();
        assert !num.isEmpty() : "Task number for deletion should not be empty!";
        if (num.isEmpty()) {
            throw new BlitzException("Delete what? Provide task number!");
        }
    
        final int idx = Integer.parseInt(num) - 1;
        assert idx >= 0 : "Invalid task number for deletion!";
        return new DeleteCommand(idx);
    }
    
    private static Command parseFind(final String input) {
        final String keyword = input.substring(4).trim();
        assert !keyword.isEmpty() : "Search keyword cannot be empty!";
        return new FindCommand(keyword);
    }
    
            throw new BlitzException("What is that? Try todo / deadline / event / mark / unmark / list/ bye / find");
        }
    }
}

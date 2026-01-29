public class Parser {
    public static Command parse(String input) throws BlitzException {
        if (input.equals("bye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }
        if (input.startsWith("todo")) {
            String desc = input.substring(4).trim();
            if (desc.isEmpty()) throw new BlitzException("What is the todo description? Give me more details!");
            return new AddCommand(desc);
        }
        if (input.startsWith("deadline")) {
            String rest = input.substring(8).trim();
            if (rest.isEmpty()) throw new BlitzException("What is the deadline? I need a deadline!");
            int byIndex = rest.indexOf("/by");
            if (byIndex == -1) throw new BlitzException("Deadline must have a /by!");
            String descrip = rest.substring(0, byIndex).trim();
            String by = rest.substring(byIndex + 3).trim();
            if (descrip.isEmpty()) throw new BlitzException("What is the activity being due? Give me the description!");
            if (by.isEmpty()) throw new BlitzException("By when? When is it due?");
            return new AddDeadlineCommand(descrip, by);
        }
        if (input.startsWith("event")) {
            String rest = input.substring(5).trim();
            int indexFrom = rest.indexOf("/from");
            int indexTo = rest.indexOf("/to");
            if (indexFrom == -1 || indexTo == -1 || indexTo < indexFrom) throw new BlitzException("What is the start and end details of this event?");
            String descrip = rest.substring(0, indexFrom).trim();
            String start = rest.substring(indexFrom + 5, indexTo).trim();
            String end = rest.substring(indexTo + 3).trim();
            if (descrip.isEmpty()) throw new BlitzException("What is the event title or name? Give me the description!");
            if (start.isEmpty() || end.isEmpty()) throw new BlitzException("Event must have both start and end time!");
            return new AddEventCommand(descrip, start, end);
        }
        if (input.startsWith("mark")) {
            String num = input.substring(4).trim();
            if (num.isEmpty()) throw new BlitzException("How do i mark something that is not there? I need to mark something!");
            int idx = Integer.parseInt(num) - 1;
            return new MarkCommand(idx);
        }
        if (input.startsWith("unmark")) {
            String num = input.substring(6).trim();
            if (num.isEmpty()) throw new BlitzException("How do i unmark something that is not there? I need to unmark something!");
            int idx = Integer.parseInt(num) - 1;
            return new UnmarkCommand(idx);
        }
        if (input.startsWith("delete")) {
            String num = input.substring(6).trim();
            if (num.isEmpty()) throw new BlitzException("Delete what? Provide task number!");
            int idx = Integer.parseInt(num) - 1;
            return new DeleteCommand(idx);
        }

        throw new BlitzException("What is that? Try todo / deadline / event / mark / unmark / list/ bye");
    }
}

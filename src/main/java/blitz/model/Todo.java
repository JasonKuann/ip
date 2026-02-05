package blitz.model;

/**
 * A simple todo task with only a description.
 */
public class Todo extends Task {

    /**
     * Construct a Todo.
     *
     * @param description description of the todo
     */
    public Todo(final String description) {
        super(description);
    }

    @Override
    public String getTaskInfo() {
        return "";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

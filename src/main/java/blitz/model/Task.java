package blitz.model;

import java.time.LocalDateTime;

/**
 * Abstract base class representing a task.
 *
 * <p>Subclasses add specific behaviour and information (deadline, event times, etc.).
 */
public abstract class Task {
    protected final String description;
    protected boolean isDone;
    protected LocalDateTime date;

    /**
     * Construct a Task.
     *
     * @param description description of the task
     */
    public Task(final String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns additional task-specific information for display (deadline date, event times).
     *
     * @return task-specific information string
     */
    public abstract String getTaskInfo();

    /**
     * Returns the parsed {@link LocalDateTime} for this task if present, otherwise null.
     *
     * @return parsed LocalDateTime or null
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Returns a short icon representing status: 'X' if done, space otherwise.
     *
     * @return status icon string
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    /** Mark this task as completed. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Mark this task as not completed. */
    public void markNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

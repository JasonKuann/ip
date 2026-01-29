package blitz.model;

import java.time.LocalDateTime;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected LocalDateTime date;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public abstract String getTaskInfo();

    public LocalDateTime getDate() {
        return date;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }
    
    public void markAsDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

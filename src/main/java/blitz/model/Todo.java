package blitz.model;

public class Todo extends Task {

    public Todo(String description) {
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
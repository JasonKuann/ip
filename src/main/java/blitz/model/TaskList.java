package blitz.model; 

import java.util.ArrayList; 
import java.util.List;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null : "Task list should not be null!";  // Added assertion
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        assert tasks != null : "Task list should not be null!";  // Added assertion
    }

    public void add(Task t) {
        assert t != null : "Task being added should not be null!";  // Added assertion
        tasks.add(t);
    }

    public boolean hasDuplicate(Task task) {
        for (Task t : tasks) {
            if (t.getDescription().equals(task.getDescription())) {
                return true;
            }
        }
        return false;
    }

    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "Invalid task index for removal!";  // Added assertion
        return tasks.remove(index);
    }

    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Invalid task index for retrieval!";  // Added assertion
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> asList() {
        return tasks;
    }
}

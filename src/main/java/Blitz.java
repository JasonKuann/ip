import java.util.Scanner;

public class Blitz {
    public static final String Line = "____________________________________________________________";
    public static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        
        System.out.println(Line);
        System.out.println(" Hello! I'm Blitz");
        System.out.println(" What can I do for you?");
        System.out.println(Line);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                System.out.println(Line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(Line);
                break;
            }

            if (input.equals("nothing")) {
                System.out.println(Line);
                System.out.println("Then i can finally enjoy some break!");
                System.out.println(Line);
                continue;
            }

            try {
                taskCount = handleInput(input, tasks, taskCount);
            } catch(BlitzException e) {
                System.out.println(Line);
                System.out.println(" " + e.getMessage());
                System.out.println(Line);
            }
        }
        scanner.close();
    }

    private static int handleInput(String input, Task[] tasks, int taskCount) throws BlitzException {
        if (input.equals("list")) {
            System.out.println(Line);
            System.out.println("Here are the tasks in your list:");
            if (taskCount == 0) {
                System.out.println("Currently no task ongoing");
            } else {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(i + 1 + ". " + tasks[i]);
                }
            }
            System.out.println(Line);
            return taskCount;
        }

        if (input.startsWith("todo")) {
            String descrip = input.substring(4).trim();
            if (descrip.isEmpty()) {
                throw new BlitzException("What is the todo description? Give me more details!");
            }
            return addTask(tasks, taskCount, new Todo(descrip));
        }

        if (input.startsWith("deadline")) {
            String restOfString = input.substring(8).trim();
            if (restOfString.isEmpty()) {
                throw new BlitzException("What is the deadline? I need a deadline!");
            }
            int byIndex = restOfString.indexOf("/by");
            if (byIndex == -1) {
                throw new BlitzException("Deadline must have a /by!");
            }
            String descrip = restOfString.substring(0, byIndex).trim();
            if (descrip.isEmpty()) {
                throw new BlitzException("What is the activity being due? Give me the description!");
            }
            String by = restOfString.substring(byIndex + 3).trim();
            if (by.isEmpty()) {
                throw new BlitzException("By when? When is it due?");
            }
            return addTask(tasks, taskCount, new Deadline(descrip, by));
        }

        if (input.startsWith("event")) {
            String restOfString = input.substring(5).trim();
            if (restOfString.isEmpty()) {
                throw new BlitzException("Event details cannot be empty? I need more information!");
            }
            int indexFrom = restOfString.indexOf("/from");
            int indexTo = restOfString.indexOf("/to");
            if (indexFrom == -1 || indexTo == -1 || indexTo < indexFrom) {
                throw new BlitzException("What is the start and end details of this event?");
            }
            String descrip = restOfString.substring(0, indexFrom).trim();
            if (descrip.isEmpty()) {
                throw new BlitzException("What is the event title or name? Give me the description!");
            }
            String startTime = restOfString.substring(indexFrom + 5, indexTo).trim();
            String endTime = restOfString.substring(indexTo + 3).trim();
            if (startTime.isEmpty() || endTime.isEmpty()) {
                throw new BlitzException("Event must have both start and end time!");
            }
            return addTask(tasks, taskCount, new Event(descrip, startTime, endTime));
        }

        if (input.startsWith("mark")) {
            String markNumber = input.substring(4).trim();
            if (markNumber.isEmpty()) {
                throw new BlitzException("How do i mark something that is not there? I need to mark something!");
            }
            int taskNumber = Integer.parseInt(markNumber);
            int index = taskNumber - 1;
            if (index < 0 || index >= taskCount) {
                throw new BlitzException("Invalid task number");
            } else {
                tasks[index].markAsDone();
                System.out.println(Line);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[index]);   
                System.out.println(Line);
            }
            return taskCount;
        }

        if (input.startsWith("unmark")) {
            String markNumber = input.substring(6).trim();
            if (markNumber.isEmpty()) {
                throw new BlitzException("How do i unmark something that is not there? I need to unmark something!");
            }
            int taskNumber = Integer.parseInt(markNumber);
            int index = taskNumber - 1;
            if (index < 0 || index >= taskCount) {
                throw new BlitzException("Invalid task number");
            } else {
                tasks[index].markNotDone();
                System.out.println(Line);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[index]);
                System.out.println(Line);
                return taskCount;
            }
        }
        throw new BlitzException("What is that? Try todo / deadline / event / mark / unmark / list/ bye");
    }

     private static int addTask(Task[] tasks, int taskCount, Task newTask) throws BlitzException {
        if (taskCount >= MAX_TASKS) {
            throw new BlitzException("I am currently sleeping, do not disturb me!");
        }

        tasks[taskCount] = newTask;
        taskCount++;

        System.out.println(Line);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + newTask);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(Line);

        return taskCount;
    }
}

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
            } else if (input.equals("nothing")) {
                System.out.println(Line);
                System.out.println("Then i can finally enjoy some break!");
                System.out.println(Line);
            } else {}
            
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
                continue;
            }

            if (input.startsWith("todo")) {
                String descrip = input.substring(5).trim();
                Task newTask = new Todo(descrip);
                taskCount = addTask(tasks, taskCount, newTask);
                continue;
            }

            if (input.startsWith("deadline")) {
                String restOfString = input.substring(9).trim();
                int byIndex = restOfString.indexOf("/by");
                String descrip = restOfString.substring(0, byIndex).trim();
                String by = restOfString.substring(byIndex + 3).trim();
                Task newTask = new Deadline(descrip, by);
                taskCount = addTask(tasks, taskCount, newTask);
                continue;
            }

            if (input.startsWith("event")) {
                String restOfString = input.substring(6).trim();
                int indexFrom = restOfString.indexOf("/from");
                int indexTo = restOfString.indexOf("/to");
                String descrip = restOfString.substring(0, indexFrom).trim();
                String startTime = restOfString.substring(indexFrom + 5, indexTo).trim();
                String endTime = restOfString.substring(indexTo + 3).trim();
                Task newTask = new Event(descrip, startTime, endTime);
                taskCount = addTask(tasks, taskCount, newTask);
                continue;
            }

            if (input.startsWith("mark ")) {
                String markNumber = input.substring(5).trim();

                int taskNumber = Integer.parseInt(markNumber);
                int index = taskNumber - 1;

                if (index < 0 || index >= taskCount) {
                    System.out.println(Line);
                    System.out.println("Invalid task number!");
                    System.out.println(Line);
                } else {
                    tasks[index].markAsDone();
                    System.out.println(Line);
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + tasks[index]);
                    System.out.println(Line);
                }
                continue;
            }

            if (input.startsWith("unmark ")) {
                String markNumber = input.substring(7).trim();

                int taskNumber = Integer.parseInt(markNumber);
                int index = taskNumber - 1;

                if (index < 0 || index >= taskCount) {
                    System.out.println(Line);
                    System.out.println(" Invalid task number!");
                    System.out.println(Line);
                } else {
                    tasks[index].markNotDone();
                    System.out.println(Line);
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + tasks[index]);
                    System.out.println(Line);
                }
                continue;
            }
            if (taskCount < MAX_TASKS) {
                tasks[taskCount] = new Task(input);
                taskCount += 1;
                System.out.println(Line);
                System.out.println("added: " + input);
                System.out.println(Line);
            } else {
                System.out.println("I am currently sleeping, do not disturb me!");
                System.out.println(Line);
            }
        }
        scanner.close();
    }

     private static int addTask(Task[] tasks, int taskCount, Task newTask) {
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

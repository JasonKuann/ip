import java.util.Scanner;

public class Blitz {
    public static final String Line = "____________________________________________________________";
    public static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[MAX_TASKS];
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

            if (taskCount < MAX_TASKS) {
                tasks[taskCount] = input;
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
}

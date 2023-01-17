import java.util.LinkedList;

public class TaskList {
    private LinkedList<Task> tasks;

    public TaskList() {
        this.tasks = new LinkedList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        String message = "I have added " + task.getTaskName() + " to my memory\n";
        message = message + "          " + task.toString() + "\n";
        message = message + "        You have " + tasks.size() + " tasks in the list";
        Response.printMessage(message);
    }

    public void deleteTask(String task) {
        // no implementation yet
    }

    public void markDone(int number) {
        tasks.get(number - 1).mark();
        Response.printMessage("Understood, I have marked the task as done:\n"
                + "        " + tasks.get(number - 1).toString());
    }
    public void markUndone(int number) {
        tasks.get(number - 1).unmark();
        Response.printMessage("Understood, I have marked the task as undone:\n"
                + "        " + tasks.get(number - 1).toString());
    }

    public void getList() {
        System.out.println("        ________________________________________________________");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("        "
                    + (i + 1)
                    + "."
                    + tasks.get(i).toString());
        }
        System.out.println("        ________________________________________________________");
    }
}

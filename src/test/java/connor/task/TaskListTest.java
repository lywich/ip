package connor.task;

import connor.ui.Ui;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void addTaskTest() {
        TaskList tasks = new TaskList();
        assertEquals(tasks.getSize(), 0);
        tasks.addTask(new Todo("work"));
        assertEquals(tasks.getSize(), 1);
        tasks.addTask(new Deadline("sleep", "2020-05-05 2000"));
        assertEquals(tasks.getSize(), 2);
        tasks.addTask(new Event("dinner", "2020-05-05 1800", "2020-05-05 2200"));
        assertEquals(tasks.getSize(), 3);
    }

    @Test
    public void deleteInvalidTaskTest() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("work"));
        tasks.addTask(new Deadline("sleep", "2020-05-05 2000"));
        tasks.addTask(new Event("dinner", "2020-05-05 1800", "2020-05-05 2200"));
        try {
            tasks.deleteTask("5", new Ui());
        } catch (InvalidTaskException e) {
            assertEquals(e.getMessage(), new InvalidTaskException().getMessage());
        }
    }

    @Test
    public void deleteValidTaskTest() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("work"));
        tasks.addTask(new Deadline("sleep", "2020-05-05 2000"));
        tasks.addTask(new Event("dinner", "2020-05-05 1800", "2020-05-05 2200"));
        try {
            tasks.deleteTask("2", new Ui());
        } catch (InvalidTaskException e) {
            assertEquals(1, 2);
        }
        assertEquals(tasks.getSize(), 2);
    }

    @Test
    public void markDoneTest() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("work"));
        tasks.addTask(new Deadline("sleep", "2020-05-05 2000"));
        tasks.addTask(new Event("dinner", "2020-05-05 1800", "2020-05-05 2200"));
        tasks.markDone(1, new Ui());
        assertEquals(tasks.getList().get(0).toString(), "[T][X] work");
    }

    @Test
    public void markUndoneTest() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("work"));
        tasks.addTask(new Deadline("sleep", "2020-05-05 2000"));
        tasks.addTask(new Event("dinner", "2020-05-05 1800", "2020-05-05 2200"));
        tasks.markDone(1, new Ui());
        assertEquals(tasks.getList().get(0).toString(), "[T][X] work");
        tasks.markUndone(1, new Ui());
        assertEquals(tasks.getList().get(0).toString(), "[T][ ] work");
    }

    @Test
    public void toStringTest() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("work"));
        tasks.addTask(new Deadline("sleep", "2020-05-05 2000"));
        String ans = "        ________________________________________________________\n" +
                "\n" +
                "        1.[T][ ] work\n" +
                "        2.[D][ ] sleep (by: MAY 5 2020 2000)\n" +
                "        I have 2 tasks in my memory\n";
        assertEquals(tasks.toString(), ans);
    }

    @Test
    public void findTest() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("work"));
        tasks.addTask(new Deadline("sleep", "2020-05-05 2000"));
        tasks.addTask(new Event("assist with work", "2020-05-05 1800", "2020-05-05 2200"));
        String output = tasks.find("work");
        String expected = "        ________________________________________________________\n"
        + "        HERE ARE THE MATCHING RESULTS:\n"
        + "        1.[T][ ] work\n"
        + "        2.[E][ ] assist with work (from: MAY 5 2020 1800 to: MAY 5 2020 2200)\n";
        assertEquals(output, expected);
    }
}

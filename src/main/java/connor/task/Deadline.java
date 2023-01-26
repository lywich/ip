package connor.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Deadline extends Task {

    private LocalDateTime deadline;
    private String dataFormat;

    public Deadline(String taskName, String DateTime) {
        super(taskName);
        this.taskName = taskName;
        try {
            this.deadline = parseDateTime(DateTime);
            this.dataFormat = dateTimeFormat(DateTime);
        } catch (DateTimeException e) {
            System.out.println("        " + e.getMessage());
        }
    }

    public Deadline(String taskName, Boolean isDone, String dataFormat) {
        super(taskName, isDone);
        this.taskName = taskName;
        this.deadline = LocalDateTime.parse(dataFormat);
        this.dataFormat = dataFormat;
    }

    @Override
    public String dataFormat() {
        return "D|" + super.dataFormat() + "|" + this.dataFormat;
    }


    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: "
                + this.formatDateTime(this.deadline)
                + ")";
    }
}

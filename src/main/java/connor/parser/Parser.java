package connor.parser;

import connor.Connor;
import connor.task.*;
import connor.ui.Ui;

public class Parser {

    /**
     * Throws InvalidTaskException if an invalid input such as "" or " ".
     *
     * @param input String that comes after command.
     * @throws InvalidTaskException if input is only blanks spaces.
     */
    private void validateName(String input) throws InvalidTaskException {
        if (input.trim().length() < 1) {
            throw new InvalidTaskException();
        }
    }

    /**
     * Returns a String array of size 2, intended to be used when parsing Deadline tasks.
     * Index 0 is the taskName and index 1 is the Deadline.
     *
     * @param input String that comes after command.
     * @return String array of only taskName and Deadline.
     * @throws InvalidTaskException if taskName is blank spaces.
     */
    private String[] getNameDeadlinePair(String input) throws InvalidTaskException {
        int byIndex = input.indexOf("/by");
        if (byIndex < 1) {
            throw new InvalidTaskException();
        }
        String[] pair = new String[2];
        pair[0] = input.substring(0, byIndex - 1);
        validateName(pair[0]);
        pair[1] = input.substring(byIndex + 4);
        return pair;
    }

    /**
     * Returns a String array of size 3, intended to be used when parsing Event tasks.
     * Index 0 is the taskName, index 1 is start period and index 2 is end period.
     *
     * @param input String that comes after command.
     * @return String array of only taskName, start period and end period.
     * @throws InvalidTaskException if taskName is blank spaces.
     */
    private String[] getNameStartEndTuple(String input) throws InvalidTaskException {
        int fromIndex = input.indexOf("/from");
        int byIndex = input.indexOf("/to");
        if (fromIndex < 1 || byIndex < 1) {
            throw new InvalidTaskException();
        }
        String[] tuple = new String[3];
        tuple[0] = input.substring(0, fromIndex - 1);
        validateName(tuple[0]);
        tuple[1] = input.substring(fromIndex + 6, byIndex - 1);
        tuple[2] = input.substring(byIndex + 4);
        return tuple;
    }

    /**
     * Returns a Task instance depending on the command.
     * Information refers to the input of the user after the command.
     * Throws InvalidTaskException if taskName is invalid.
     *
     * @param command Command of the input.
     * @param information String that comes after command.
     * @return Task instance.
     * @throws InvalidTaskException if taskName is blank spaces.
     */
    public Task parseCommand(String command, String information) throws InvalidTaskException {
        if (command.equals("TODO")) {
            validateName(information);
            return new Todo(information);
        } else if (command.equals("DEADLINE")) {
            String[] pair = getNameDeadlinePair(information);
            return new Deadline(pair[0], pair[1]);
        } else if (command.equals("EVENT")){
            String[] tuple = getNameStartEndTuple(information);
            return new Event(tuple[0], tuple[1], tuple[2]);
        } else {
            throw new InvalidTaskException();
        }
    }

    /**
     * Returns the String that comes after the command.
     * This should only be called for inputs with more than 1 word.
     *
     * @param input Full user input String.
     * @return String that comes after the command.
     * @throws InvalidTaskException if there is no String after the command
     */
    private String getTask(String input) throws InvalidTaskException {
        if (input.indexOf(' ') == -1) {
            throw new InvalidTaskException();
        }
        return input.substring(input.indexOf(' ') + 1).trim();
    }

    /**
     * Returns the command of the input.
     *
     * @param input the first word of the user input String.
     * @return String of the input command.
     */
    private String getCommand(String input) {
        if (input.indexOf(' ') == -1) {
            return input.toUpperCase();
        } else {
            return input.substring(0, input.indexOf(' ')).toUpperCase();
        }
    }

    /**
     * Returns true if the input command is valid.
     * Else, returns false and print the corresponding error message.
     *
     * @param input Full user input String.
     * @param tasks current collection of Tasks.
     * @param ui UI to print messages.
     * @return true if valid command, false otherwise.
     */
    public boolean parse(String input, TaskList tasks, Ui ui) {
        String command = getCommand(input).trim();
        try {
            switch (Connor.Commands.valueOf(command)) {
            case HI:
                ui.greetings("HI");
                break;

            case BYE:
                ui.greetings("BYE");
                return true;

            case LIST:
                System.out.println(tasks.toString());
                break;

            case MARK:
                tasks.markDone(Integer.parseInt(getTask(input)), ui);
                break;

            case UNMARK:
                tasks.markUndone(Integer.parseInt(getTask(input)), ui);
                break;

            case TODO:
            case DEADLINE:
            case EVENT:
                Task task = parseCommand(command, getTask(input));
                tasks.addTask(task);
                ui.addTaskMessage(task, tasks.getSize());
                break;

            case DELETE:
                tasks.deleteTask(getTask(input), ui);
                break;

            case DELETEALL:
                tasks.deleteAllTask();
                ui.deleteAllMessage();
                break;

            case FIND:
                System.out.println(tasks.find(getTask(input)));
                break;
            }
        } catch (IllegalArgumentException e) {
            Ui.printMessage("INVALID COMMAND");
        } catch (InvalidTaskException e) {
            Ui.printMessage(e.getMessage());
        }
        return false;
    }
}

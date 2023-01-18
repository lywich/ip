public class Responses {
    public static String LINE = "        ________________________________________________________\n";

    public static void printMessage(String response) {
        System.out.println(LINE + "        " + response);
    }

    public static void greetings(String responseType) {
        switch (responseType) {
            case "HI":
                printMessage("Hi, I hope that you are having a nice day.");
                break;

            case "BYE":
                printMessage("It was a good session Hank, Bye.");
                break;
        }
    }
}
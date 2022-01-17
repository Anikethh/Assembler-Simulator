package bonus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        init();
    }

    public static void init() throws IOException {
        InstructionTable.populate();
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> input = new ArrayList<>();
        while (true) {
            String line = scanner.nextLine();
            input.add(line);
            if(line.startsWith("10011")) {
                break;
            }
        }
        Execute.init(input);
    }
}
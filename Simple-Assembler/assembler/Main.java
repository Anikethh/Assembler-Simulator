package assembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        InstructionTable.populate();
        ArrayList<String> rawInput = new ArrayList<>();
        ArrayList<ArrayList<String>> input = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int lineNumber = 0;
        int currLine = 1;
        int numberOfVariables = 0;
        try {
            while (true) {
                String line = scanner.nextLine();
                if(Objects.equals(line, "")) {
                    continue;
                }
                line = line.strip();
                rawInput.add(line);
                String[] splitLine = line.split("\\s+");
                ArrayList<String> tokens = new ArrayList<>(Arrays.asList(splitLine));
                input.add(lineNumber++, tokens);
                if (splitLine[splitLine.length - 1].equals("hlt")) {
                    break;
                }
                if (!scanner.hasNextLine()) {
                    throw new IllegalStateException("No hlt statement at the end");
                }
                currLine++;
            }
            if (scanner.hasNextLine()) {
                throw new IllegalStateException("hlt should be at the end of the code");
            }

            Error.init(input);

            currLine = 1;
            try {
                for (ArrayList<String> line : input) {
                    String first = line.get(0);
                    if (isLabel(first)) {
                        String second = line.get(1);
                        if (second.equals("mov")) {
                            String fourth = line.get(3);
                            if (UtilityFunctions.isRegister(fourth)) {
                                line.set(1, "mvr");
                            }
                        }
                    }
                    else if (first.equals("mov")) {
                        String third = line.get(2);
                        if (UtilityFunctions.isRegister(third)) {
                            line.set(0, "mvr");
                        }
                    }
                    currLine++;
                }
            } catch (IllegalStateException | NullPointerException e) {
                throw e;
            }
            catch (Exception e) {
                throw new Exception("Wrong syntax");
            }


            currLine = 1;
            int size = 0;
            for (ArrayList<String> line : input) {
                String first = line.get(0);
                boolean variable = first.equals("var");
                if (variable) {
                    String second = line.get(1);
                    VariableTable.addVariable(second, "");
                    numberOfVariables++;
                }
                else if (isLabel(first)) {
                    LabelTable.addLabel(first, String.valueOf(size++));
                }
                else {
                    size++;
                }
                currLine++;
            }
            VariableTable.updateAddresses(size);

            currLine = 1;
            int codeStart = VariableTable.getSize();
            for (int i = codeStart; i < input.size(); i++) {
                ArrayList<String> line = input.get(i);
                String first = line.get(0);
                if(isVarKeyWord(first)) {
                    throw new Exception("All variables should be declared at the top");
                }
                if (isLabel(first)) {
                    ArrayList<String> temp = new ArrayList<>(line);
                    temp.remove(0);
                    Encode.generate(temp, currLine + numberOfVariables);
                }
                else {
                    Encode.generate(line, currLine + numberOfVariables);
                }
                currLine++;
            }

            Encode.printBinary();
        }
        catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            int line = currLine + numberOfVariables;
            System.out.println("Error at line " + line + " - '" + rawInput.get(line - 1) + "'");
            System.out.println(e.getMessage());
        }
    }

    private static boolean isLabel(String token) {
        return UtilityFunctions.isLabel(token);
    }

    private static boolean isVarKeyWord(String token) {
        return UtilityFunctions.isVariableKeyWord(token);
    }
}
package assembler;

import java.util.ArrayList;

public class Encode {

    private static final ArrayList<String> binary = new ArrayList<>();
    private static int lineNumber;
    
    public static void printBinary() {
        for(String s : binary) {
            System.out.println(s);
        }
    }

    public static void generate(ArrayList<String> line, int lineNumber) throws Exception {
        Encode.lineNumber = lineNumber;
        String first = line.get(0);
        char type = UtilityFunctions.getType(first);
        try {
            if(type == 'A') {
                typeA(line);
            }
            else if(type == 'B') {
                typeB(line);
            }
            else if(type == 'C') {
                typeC(line);
            }
            else if(type == 'D') {
                typeD(line);
            }
            else if(type == 'E') {
                typeE(line);
            }
            else {
                typeF();
            }
        }
        catch (IllegalStateException | NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Wrong syntax of " + first + " instruction");
        }
    }

    private static void typeA(ArrayList<String> line) throws Exception {
        String instruction = line.get(0);
        String reg1 = line.get(1);
        String reg2 = line.get(2);
        String reg3 = line.get(3);

        String opcode = UtilityFunctions.getOpcode(instruction);
        String filler = "00";
        String reg1Code = UtilityFunctions.getRegisterCode(reg1);
        String reg2Code = UtilityFunctions.getRegisterCode(reg2);
        String reg3Code = UtilityFunctions.getRegisterCode(reg3);

        String res = opcode + filler + reg1Code + reg2Code + reg3Code;
        binary.add(res);
    }

    private static void typeB(ArrayList<String> line) throws Exception {
        String instruction = line.get(0);
        String reg = line.get(1);
        String immediate = line.get(2);

        String opcode = UtilityFunctions.getOpcode(instruction);
        String regCode = UtilityFunctions.getRegisterCode(reg);
        String immInBin = UtilityFunctions.immediateToBinary(immediate);

        String res = opcode + regCode + immInBin;
        binary.add(res);

        if(reg.equals("FLAGS")) {
            throw new IllegalStateException("Error at line " + lineNumber +
                    " - " + toString(line) + "\nIllegal use of FLAGS register");
        }

        if(immInBin == null) {
            throw new IllegalStateException("Error at line " + lineNumber +
                    " - " + toString(line) + "\nImmediate should be preceded by $ symbol");
        }
    }

    private static void typeC(ArrayList<String> line) throws Exception {
        String instruction = line.get(0);
        String reg1 = line.get(1);
        String reg2 = line.get(2);

        String opcode = UtilityFunctions.getOpcode(instruction);
        String filler = "00000";
        String reg1Code = UtilityFunctions.getRegisterCode(reg1);
        String reg2Code = UtilityFunctions.getRegisterCode(reg2);

        String res = opcode + filler + reg1Code + reg2Code;
        binary.add(res);

        if(reg1.equals("FLAGS")) {
            throw new IllegalStateException("Error at line " + lineNumber + " - " + toString(line) +
                    "\nIllegal use of FLAGS register");
        }
    }

    private static void typeD(ArrayList<String> line) throws Exception {
        String instruction = line.get(0);
        String reg = line.get(1);
        String variable = line.get(2);

        String opcode = UtilityFunctions.getOpcode(instruction);
        String regCode = UtilityFunctions.getRegisterCode(reg);
        String variableAddress = UtilityFunctions.getVariableAddress(variable);

        String res = opcode + regCode + variableAddress;
        binary.add(res);
    }

    private static void typeE(ArrayList<String> line) throws Exception {
        String instruction = line.get(0);
        String label = line.get(1);

        String opcode = UtilityFunctions.getOpcode(instruction);
        String filler = "000";
        String address = UtilityFunctions.getLabelAddress(label);

        String res = opcode + filler + address;
        binary.add(res);
    }

    private static void typeF() {
        binary.add("1001100000000000");
    }

    private static String toString(ArrayList<String> line) {
        String res = "'";
        for(String s : line) {
            res += s + " ";
        }
        res += "'";
        return res;
    }
}

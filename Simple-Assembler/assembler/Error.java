package assembler;

import java.util.ArrayList;

public class Error {
    
    public static void init(ArrayList<ArrayList<String>> input) throws Exception {
        imm_contain_alphabets(input);
        imm_outside_range(input);
        error_in_hlt(input);
        invalid_var_or_label_name(input);
        isReservedWord(input);
    }

    private static void error_in_hlt(ArrayList<ArrayList<String>> input) throws IllegalStateException {
        int count = 0, size = input.size();
        for(ArrayList<String> line : input) {
            for(String token : line) {
                if(token.equals("hlt")) {
                    count++;
                }
            }
        }
        if(count != 1) {
            throw new IllegalStateException("There should be exactly one hlt in the whole program");
        }
        if(!input.get(size - 1).get(0).equals("hlt") && (input.get(size-1).size() > 1 && !input.get(size-1).get(1).equals("hlt"))) {
            throw new IllegalStateException("hlt not present at the end or wrong syntax");
        }
    }

    private static void imm_contain_alphabets(ArrayList<ArrayList<String>> input) throws IllegalStateException {
        int lineNumber = 1;
        for(ArrayList<String> line : input) {
            for(String token : line) {
                if(UtilityFunctions.isImmediate(token)) {
                    try {
                        Integer.parseInt(token.substring(1));
                    } 
                    catch (IllegalStateException e) {
                        throw new IllegalStateException("Error at line " + lineNumber + 
                                " - " + toString(line) + "\nImmediate should contain only integers");
                    }
                }
            }
            lineNumber++;
        }
    }

    private static void imm_outside_range(ArrayList<ArrayList<String>> input) throws IllegalStateException {
        int lineNumber = 1;
        for(ArrayList<String> line : input) {
            for(String token : line) {
                if(UtilityFunctions.isImmediate(token)) {
                    int value = Integer.parseInt(token.substring(1));
                    if(value < 0 || value > 255) {
                        throw new IllegalStateException("Error at line " + lineNumber + 
                                " - " + toString(line) + "\nImmediate value out of range");
                    }
                }
            }
            lineNumber++;
        }
    }

    private static void invalid_var_or_label_name(ArrayList<ArrayList<String>> input) throws IllegalStateException {
        int lineNumber = 1;
        for(ArrayList<String> line : input) {
            for(String token : line) {
                if(UtilityFunctions.isLabel(token)) {
                    for(char c : token.toCharArray()) {
                        if( !((c >= 48 && c <= 58) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c == 95)) ) {
                            throw new IllegalStateException("Error at line " + lineNumber + " - " + toString(line) +
                            "\nLabel name can only contain alphanumeric characters and underscores - '" + token + "'");
                        }
                    }
                }
                else if(UtilityFunctions.isVariableKeyWord(token)) {
                    if(line.size() < 2) {
                        throw new IllegalStateException("Error at line " + lineNumber + " - " + toString(line) +
                                "\nNo variable name detected");
                    }
                    String var = line.get(1);
                    for(char c : var.toCharArray()) {
                        if( !((c >= 48 && c <= 58) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c == 95)) ) {
                            throw new IllegalStateException("Error at line " + lineNumber + " - " + toString(line) +
                            "\nVar name can only contain alphanumeric characters and underscores - '" + var + "'");
                        }
                    }
                }
            }
            lineNumber++;
        }
    }

    private static void isReservedWord(ArrayList<ArrayList<String>> input) throws Exception {
        int lineNumber = 1;
        for(ArrayList<String> line : input) {
            String token = line.get(0);
            if(UtilityFunctions.isLabel(token)) {
                token = token.substring(0, token.length()-1);
                if(InstructionTable.isPresent(token) || token.equals("hlt") || token.equals("var")) {
                    throw new IllegalStateException("Error at line " + lineNumber + " - " + toString(line) +
                            "\nUse of reserved keyword - '" + token + "'");
                }
            }
            else if(UtilityFunctions.isVariableKeyWord(token)) {
                String var = line.get(1);
                if(InstructionTable.isPresent(var) || var.equals("hlt") || var.equals("var")) {
                    throw new IllegalStateException("Error at line " + lineNumber +  " - " + toString(line) +
                    "\nUse of reserved keyword - '" + var + "'");
                }
            }
            lineNumber++;
        }
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

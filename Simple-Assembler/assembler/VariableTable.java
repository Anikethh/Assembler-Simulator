package assembler;

import java.util.HashMap;
import java.util.Map;

public class VariableTable {

    private static final Map<String, String> map = new HashMap<>();

    public static void addVariable(String var, String address) {
        if(isPresent(var)) {
            throw new IllegalStateException("Variable already declared - '" + var + "'");
        }
        map.put(var, address);
    }

    public static String getAddress(String variable) {
        if (!isPresent(variable))  {
            throw new NullPointerException("Typo in variable name or variable not declared - '" + variable + "'");
        }
        return map.get(variable);
    }

    public static void updateAddresses(int address) {
        for(String var : map.keySet()) {
            map.replace(var, String.valueOf(address++));
        }
    }

    public static boolean isPresent(String variable) {
        return map.containsKey(variable);
    }

    public static int getSize() {
        return map.size();
    }
}

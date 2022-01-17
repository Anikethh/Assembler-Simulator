package assembler;

import java.util.HashMap;
import java.util.Map;

public class InstructionTable {

    private static final Map<String, Instruction> map = new HashMap<>();

    public static void populate() {
        map.put("add", new Instruction("add", "00000", 'A'));
        map.put("sub", new Instruction("sub", "00001", 'A'));
        map.put("mov", new Instruction("mov", "00010", 'B'));
        map.put("mvr", new Instruction("mvr", "00011", 'C'));
        map.put("ld",  new Instruction("ld" , "00100", 'D'));
        map.put("st",  new Instruction("st" , "00101", 'D'));
        map.put("mul", new Instruction("mul", "00110", 'A'));
        map.put("div", new Instruction("div", "00111", 'C'));
        map.put("rs",  new Instruction("rs" , "01000", 'B'));
        map.put("ls",  new Instruction("ls" , "01001", 'B'));
        map.put("xor", new Instruction("xor", "01010", 'A'));
        map.put("or",  new Instruction("or" , "01011", 'A'));
        map.put("and", new Instruction("and", "01100", 'A'));
        map.put("not", new Instruction("not", "01101", 'C'));
        map.put("cmp", new Instruction("cmp", "01110", 'C'));
        map.put("jmp", new Instruction("jmp", "01111", 'E'));
        map.put("jlt", new Instruction("jlt", "10000", 'E'));
        map.put("jgt", new Instruction("jgt", "10001", 'E'));
        map.put("je",  new Instruction("je" , "10010", 'E'));
        map.put("hlt", new Instruction("hlt", "10011", 'F'));
    }

    public static Instruction getInstruction(String key) {
        if(!isPresent(key)) {
            throw new NullPointerException("Typo in instruction name - '" + key + "'");
        }
        return map.get(key);
    }

    public static boolean isPresent(String key) {
        return map.containsKey(key);
    }
}

package bonus;

import java.util.HashMap;
import java.util.Map;

public class InstructionTable {
    private static final Map<String, Instruction> map = new HashMap<>();

    public static void populate() {
        map.put("00000", new Instruction("add", "00000", 'A'));
        map.put("00001", new Instruction("sub", "00001", 'A'));
        map.put("00010", new Instruction("mov", "00010", 'B'));
        map.put("00011", new Instruction("mvr", "00011", 'C'));
        map.put("00100", new Instruction("ld" , "00100", 'D'));
        map.put("00101", new Instruction("st" , "00101", 'D'));
        map.put("00110", new Instruction("mul", "00110", 'A'));
        map.put("00111", new Instruction("div", "00111", 'C'));
        map.put("01000", new Instruction("rs" , "01000", 'B'));
        map.put("01001", new Instruction("ls" , "01001", 'B'));
        map.put("01010", new Instruction("xor", "01010", 'A'));
        map.put("01011", new Instruction("or" , "01011", 'A'));
        map.put("01100", new Instruction("and", "01100", 'A'));
        map.put("01101", new Instruction("not", "01101", 'C'));
        map.put("01110", new Instruction("cmp", "01110", 'C'));
        map.put("01111", new Instruction("jmp", "01111", 'E'));
        map.put("10000", new Instruction("jlt", "10000", 'E'));
        map.put("10001", new Instruction("jgt", "10001", 'E'));
        map.put("10010", new Instruction("je" , "10010", 'E'));
        map.put("10011", new Instruction("hlt", "10011", 'F'));
    }

    public static Instruction getInstruction(String opcode) {
        return map.get(opcode);
    }
}

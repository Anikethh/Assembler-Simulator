package bonus;

import java.io.*;
import java.util.ArrayList;

public class Execute {

    private static final Register R0 = new Register(), R1 = new Register(), R2 = new Register(), R3 = new Register(),
            R4 = new Register(), R5 = new Register(), R6 = new Register();

    private static final Flags FLAGS = new Flags();
    private static int PC = 0;

    static FileWriter fw;

    public static void init(ArrayList<String> input) throws IOException {
        Memory.init(input);
        File file = new File("src/bonus/input.txt");
        file.delete();
        file.createNewFile();
        fw = new FileWriter("src/bonus/input.txt", true);
        boolean halted = false;
        int prev = -1;
        while (!halted) {
            String binary = Memory.getBinary(PC);
            String opcode = getOpcode(binary);
            char type = getType(opcode);
            int updatedPC = -1;
            if(type == 'A') {
                updatedPC = typeA(binary);
            }
            else if(type == 'B') {
                updatedPC = typeB(binary);
            }
            else if(type == 'C') {
                updatedPC = typeC(binary);
            }
            else if(type == 'D') {
                updatedPC = typeD(binary);
            }
            else if(type == 'E') {
                updatedPC = typeE(binary);
            }
            else {
                halted = true;
            }


            if(!halted)
                write();
            else
                fw.write(String.valueOf(prev));
            PC = updatedPC;
            prev = PC;
        }
        fw.close();
    }



    private static void write() throws IOException {
        fw.write(String.valueOf(PC));
        fw.write("\n");
    }

    private static void writeMemory(int index) throws IOException {
        fw.write(PC + " " + index);
        fw.write("\n");
    }


    private static String getOpcode(String binary) {
        return binary.substring(0, 5);
    }

    private static char getType(String opcode) {
        return InstructionTable.getInstruction(opcode).getType();
    }

    private static int binaryToInteger(String binary) {
        return Integer.parseInt(binary, 2);
    }

    private static String getInstructionName(String opcode) {
        return InstructionTable.getInstruction(opcode).getName();
    }





    private static Register pointToRegister(int value) {
        if(value == 0) {
            return R0;
        }
        if(value == 1) {
            return R1;
        }
        if(value == 2) {
            return R2;
        }
        if(value == 3) {
            return R3;
        }
        if(value == 4) {
            return R4;
        }
        if(value == 5) {
            return R5;
        }
        if(value == 6) {
            return R6;
        }
        Register r = new Register();
        int data = binaryToInteger(FLAGS.getDataBinary());
        r.setData(data);
        return r;
    }





    private static int typeA(String binary) {
        String opcode = getOpcode(binary);
        String name = getInstructionName(opcode);

        int val1 = binaryToInteger(binary.substring(7, 10));
        Register reg1 = pointToRegister(val1);

        int val2 = binaryToInteger(binary.substring(10, 13));
        Register reg2 = pointToRegister(val2);

        int val3 = binaryToInteger(binary.substring(13, 16));
        Register reg3 = pointToRegister(val3);

        FLAGS.clear();
        boolean overflow = false;
        if(name.equals("add")) {
            int val = reg2.getData() + reg3.getData();
            overflow = reg1.setData(val);
        }
        else if(name.equals("sub")) {
            int val = reg2.getData() - reg3.getData();
            overflow = reg1.setData(val);
        }
        else if(name.equals("mul")) {
            int val = reg2.getData() * reg3.getData();
            overflow = reg1.setData(val);
        }
        else if(name.equals("and")) {
            int val = reg2.getData() & reg3.getData();
            reg1.setData(val);
        }
        else if(name.equals("or")) {
            int val = reg2.getData() | reg3.getData();
            reg1.setData(val);
        }
        else if(name.equals("xor")) {
            int val = reg2.getData() ^ reg3.getData();
            reg1.setData(val);
        }
        FLAGS.setV(overflow ? 1 : 0);
        return PC+1;
    }

    private static int typeB(String binary) {
        String opcode = getOpcode(binary);
        String name = getInstructionName(opcode);

        int val = binaryToInteger(binary.substring(5, 8));
        Register reg = pointToRegister(val);

        int immediate = binaryToInteger(binary.substring(8, 16));

        if(name.equals("mov")) {
            reg.setData(immediate);
        }
        else if(name.equals("ls")) {
            int v = reg.getData() >> immediate;
            reg.setData(v);
        }
        else if(name.equals("rs")) {
            int v = reg.getData() << immediate;
            reg.setData(v);
        }
        FLAGS.clear();
        return PC+1;
    }

    private static int typeC(String binary) {
        String opcode = getOpcode(binary);
        String name = getInstructionName(opcode);

        int val1 = binaryToInteger(binary.substring(10, 13));
        Register reg1 = pointToRegister(val1);

        int val2 = binaryToInteger(binary.substring(13, 16));
        Register reg2 = pointToRegister(val2);

        FLAGS.clear();
        val1 = reg1.getData();
        val2 = reg2.getData();
        if(name.equals("mvr")) {
            reg1.setData(val2);
        }
        else if(name.equals("not")) {
            reg1.setData(~val2);
        }
        else if(name.equals("div")) {
            int val = val1 / val2;
            reg1.setData(val);
        }
        else if(name.equals("cmp")) {
            if(val1 == val2) {
                FLAGS.setE(1);
            }
            else if(val1 > val2) {
                FLAGS.setG(1);
            }
            else {
                FLAGS.setL(1);
            }
        }
        return PC+1;
    }

    private static int typeD(String binary) throws IOException {
        String opcode = getOpcode(binary);
        String name = getInstructionName(opcode);

        int val = binaryToInteger(binary.substring(5, 8));
        Register reg = pointToRegister(val);

        int index = binaryToInteger(binary.substring(8, 16));
        writeMemory(index);

        if(name.equals("ld")) {
            String bin = Memory.getBinary(index);
            val = binaryToInteger(bin);
            reg.setData(val);
        }
        else if(name.equals("st")) {
            Memory.setData(reg.getData(), index);
        }

        FLAGS.clear();
        return PC+1;
    }

    private static int typeE(String binary) {
        String opcode = getOpcode(binary);
        String name = getInstructionName(opcode);

        int immediate = binaryToInteger(binary.substring(8, 16));
        int res = -1;
        if(name.equals("jmp")) {
            res = immediate;
        }
        else if(name.equals("jlt")) {
            if(FLAGS.getL() == 1) {
                res = immediate;
            }
            else
                res = PC+1;
        }
        else if(name.equals("jgt")) {
            if(FLAGS.getG() == 1) {
                res = immediate;
            }
            else
                res = PC+1;
        }
        else if(name.equals("je")) {
            if(FLAGS.getE() == 1) {
                res = immediate;
            }
            else
                res = PC+1;
        }
        FLAGS.clear();
        return res;
    }
}

package assembler;

public class Instruction {
    private final String name, opcode;
    private final char type;

    public Instruction(String name, String opcode, char type) {
        this.name = name;
        this.opcode = opcode;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getOpcode() {
        return opcode;
    }

    public char getType() {
        return type;
    }
}

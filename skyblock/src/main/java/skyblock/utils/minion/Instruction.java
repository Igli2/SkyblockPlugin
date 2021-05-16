package skyblock.utils.minion;

public class Instruction {

    public enum InstructionType {
        JMP_IF_TRUE,
        JMP_IF_FALSE,
        BREAK,
        SELECT,
        PLACE,
        USE,
        DROP,
        THROW,
        CRAFT
    }

    private InstructionType type;
    private String arg;

    public Instruction(InstructionType type, String arg) {
        this.type = type;
        this.arg = arg;
    }

    public Instruction(InstructionType type) {
        this.type = type;
        this.arg = "";
    }

    public InstructionType getType() {
        return type;
    }

    public String getArg() {
        return arg;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "type=" + type +
                ", arg='" + arg + '\'' +
                '}';
    }
}

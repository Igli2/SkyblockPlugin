package skyblock.utils.minion;

import java.util.ArrayList;

public class InstructionCodeGenerator {

    public static ArrayList<Instruction> generateInstructions(Branch branch) throws Exception {
        ArrayList<Instruction> instructions = new ArrayList<>();

        if(branch instanceof  CommandBranch) {
            CommandBranch command = (CommandBranch) branch;
            switch(command.getType()) {
                case BREAK:
                    instructions.add(new Instruction(Instruction.InstructionType.BREAK));
                    break;
                case PLACE:
                    instructions.add(new Instruction(Instruction.InstructionType.PLACE));
                    break;
                case CRAFT:
                    instructions.add(new Instruction(Instruction.InstructionType.CRAFT, String.valueOf(command.getArg())));
                    break;
                case DROP:
                    instructions.add(new Instruction(Instruction.InstructionType.DROP, String.valueOf(command.getArg())));
                    break;
                case THROW:
                    instructions.add(new Instruction(Instruction.InstructionType.THROW, String.valueOf(command.getArg())));
                    break;
                case SELECT:
                    instructions.add(new Instruction(Instruction.InstructionType.SELECT, String.valueOf(command.getArg())));
                    break;
                case USE:
                    instructions.add(new Instruction(Instruction.InstructionType.USE));
                    break;
                case LEFT:
                    instructions.add(new Instruction(Instruction.InstructionType.LEFT));
                    break;
                case RIGHT:
                    instructions.add(new Instruction(Instruction.InstructionType.RIGHT));
                    break;
                default:
                    throw new Exception("Invalid command type!");
            }
        } else if(branch instanceof ConditionalBranch) {
            ConditionalBranch conditional = (ConditionalBranch) branch;
            ArrayList<Instruction> childInstructions = InstructionCodeGenerator.generateInstructions(conditional.getChild());

            for(int c = 0; c < conditional.getConditions().length; c++) {
                Condition condition = conditional.getConditions()[c];

                int offset = conditional.getConditions().length + childInstructions.size() - c;
                instructions.add(new Instruction((condition.isNegated()) ? Instruction.InstructionType.JMP_IF_TRUE : Instruction.InstructionType.JMP_IF_FALSE, condition.getIdentifier() + ";" + offset));
            }
            instructions.addAll(childInstructions);

        } else {
            SequenceBranch sequence = (SequenceBranch) branch;
            for(Branch child : sequence.getChildren()) {
                instructions.addAll(InstructionCodeGenerator.generateInstructions(child));
            }
        }

        return instructions;
    }
}

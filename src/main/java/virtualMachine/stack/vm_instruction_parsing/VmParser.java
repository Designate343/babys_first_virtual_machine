package virtualMachine.stack.vm_instruction_parsing;

import virtualMachine.stack.datawrappers.instruction.Commands;
import virtualMachine.stack.datawrappers.instruction.Instruction;
import virtualMachine.stack.memory.GlobalVirtualMemory;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.AllInstructionProcessors;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

import java.util.List;

import static virtualMachine.stack.datawrappers.instruction.Commands.*;

public class VmParser {

    private final List<Instruction> vmInstructions;
    private final GlobalVirtualMemory globalVirtualMemory = new GlobalVirtualMemory();
    private final AllInstructionProcessors instructionProcessors = new AllInstructionProcessors();

    public VmParser(List<Instruction> vmInstructions) {
        this.vmInstructions = vmInstructions;

        initializeFunctions(vmInstructions);
    }

    public void processInstruction(Instruction instruction) {
        var instructionProcessor = getInstructionProcessorFromCommand(instruction);
        instructionProcessor.processInstruction(instruction, globalVirtualMemory);
    }

    public void executeVmInstructions() {
        final var totalInstructions = vmInstructions.size();

        while (globalVirtualMemory.getControlFlow().hasNextInstruction(totalInstructions)) {
            int instructionPointer = globalVirtualMemory.getControlFlow().nextInstruction();

            Instruction instruction = vmInstructions.get(instructionPointer);
            System.out.println(instruction.toString());
            var instructionProcessor = getInstructionProcessorFromCommand(instruction);
            instructionProcessor.processInstruction(instruction, globalVirtualMemory);
        }

    }

    private InstructionProcessor getInstructionProcessorFromCommand(Instruction instruction) {
        Commands command = instruction.getCommand();

        if (command == POP) {

            return instructionProcessors.getPopInstructionProcessor();
        } else if (command == PUSH) {

            return instructionProcessors.getPushInstructionProcessor();
        } else if (Commands.allBinaryCommands().contains(command)) {

            return instructionProcessors.getBinaryInstructionProcessor();
        } else if (Commands.allUnaryCommands().contains(command)) {

            return instructionProcessors.getUnaryInstructionProcessor();
        } else if (Commands.allFunctionCommands().contains(command)) {

            return instructionProcessors.getFunctionProcessor();
        } else if (Commands.programFlowCommands().contains(command)) {

            return instructionProcessors.getProgramFlowProcessor();
        } else {
            throw new RuntimeException("Unknown instruction: " + instruction);
        }
    }

    public GlobalVirtualMemory getVirtualMemory() {
        return globalVirtualMemory;
    }


    private void initializeFunctions(List<Instruction> vmInstructions) {
        for (int i = 0; i < vmInstructions.size(); i++) {
            var instruction = vmInstructions.get(i);
            if (instruction.getCommand() == FUNCTION
                    || instruction.getCommand() == LABEL) {

                globalVirtualMemory.getControlFlow().addJumpLocation(instruction.getOperand(), i);
            }
        }
    }
}

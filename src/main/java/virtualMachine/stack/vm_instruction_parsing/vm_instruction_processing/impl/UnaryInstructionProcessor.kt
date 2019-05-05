package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.memory.GlobalVirtualMemory
import virtualMachine.stack.datawrappers.Word
import virtualMachine.stack.datawrappers.instruction.Commands
import virtualMachine.stack.datawrappers.instruction.Instruction
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class UnaryInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: Instruction, virtualMemory: GlobalVirtualMemory) {
        val uniaryInstruction: Word = virtualMemory.popStack()

        if (instruction == Commands.NOT) {
            virtualMemory.pushToStack(uniaryInstruction.bitWiseNot())
        } else if (instruction == Commands.NEGATIVE) {
            virtualMemory.pushToStack(uniaryInstruction.negate());
        }
    }
}
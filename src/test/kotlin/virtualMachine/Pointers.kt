package virtualMachine

import org.junit.Test
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser
import kotlin.test.assertEquals

class Pointers {

    @Test
    fun canSetPointer() {
        val line1 = "push constant 1450"
        val line2 = "pop pointer 0"

        val vmProcessor = VMInstructionParser()
        val stack = InstructionStack()
        vmProcessor.processInstruction(stack, line1)
        vmProcessor.processInstruction(stack, line2)

        assertEquals(1450, vmProcessor.getThis())
    }

}
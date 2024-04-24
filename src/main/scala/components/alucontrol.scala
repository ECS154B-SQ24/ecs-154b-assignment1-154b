// This file contains ALU control logic.

package dinocpu.components

import chisel3._
import chisel3.util._

/**
 * The ALU control unit
 *
 * Input:  aluop        Specifying the type of instruction using ALU
 *                          . 0 for none of the below
 *                          . 1 for 64-bit R-type
 *                          . 2 for 64-bit I-type
 *                          . 3 for 32-bit R-type
 *                          . 4 for 32-bit I-type
 *                          . 5 for non-arithmetic instruction types that uses ALU (auipc/jal/jarl/Load/Store)
 * Input:  funct7       The most significant bits of the instruction.
 * Input:  funct3       The middle three bits of the instruction (12-14).
 *
 * Output: operation    What we want the ALU to do.
 *
 * For more information, see Section 4.4 and A.5 of Patterson and Hennessy.
 * This is loosely based on figure 4.12
 */
class ALUControl extends Module {
  val io = IO(new Bundle {
    val aluop     = Input(UInt(3.W))
    val funct7    = Input(UInt(7.W))
    val funct3    = Input(UInt(3.W))

    val operation = Output(UInt(5.W))
  })

  io.operation := "b11111".U // Invalid

  //Your code goes here
  when(io.aluop === 1.U){ // 64 bit R-type
    when((io.funct3 === "b000".U)&(io.funct7 === "b0000000".U)) {io.operation := "b00001".U} //add
    .elsewhen((io.funct3 === "b000".U) & (io.funct7 === "b0100000".U)) {io.operation := "b00100".U} //sub
    .elsewhen((io.funct3 === "b001".U) & (io.funct7 === "b0000000".U)) {io.operation := "b10010".U} //sll
    .elsewhen((io.funct3 === "b010".U) & (io.funct7 === "b0000000".U)) {io.operation := "b10110".U} //slt
    .elsewhen((io.funct3 === "b011".U) & (io.funct7 === "b0000000".U)) {io.operation := "b10111".U} //sltu
    .elsewhen((io.funct3 === "b100".U) & (io.funct7 === "b0000000".U)) {io.operation := "b01111".U} //xor
    .elsewhen((io.funct3 === "b101".U) & (io.funct7 === "b0000000".U)) {io.operation := "b10100".U} //srl
    .elsewhen((io.funct3 === "b101".U) & (io.funct7 === "b0100000".U)) {io.operation := "b10000".U} //sra
    .elsewhen((io.funct3 === "b110".U) & (io.funct7 === "b0000000".U)) {io.operation := "b01110".U} //or
    .elsewhen((io.funct3 === "b111".U) & (io.funct7 === "b0000000".U)) {io.operation := "b01101".U} //and
    .elsewhen((io.funct3 === "b000".U) & (io.funct7 === "b0000001".U)) {io.operation := "b00110".U} //mul
    .elsewhen((io.funct3 === "b001".U) & (io.funct7 === "b0000001".U)) {io.operation := "b00111".U} //mulh
    .elsewhen((io.funct3 === "b010".U) & (io.funct7 === "b0000001".U)) {io.operation := "b11000".U} //mulhsu
    .elsewhen((io.funct3 === "b011".U) & (io.funct7 === "b0000001".U)) {io.operation := "b01000".U} //mulhu
    .elsewhen((io.funct3 === "b100".U) & (io.funct7 === "b0000001".U)) {io.operation := "b01011".U} //div
    .elsewhen((io.funct3 === "b101".U) & (io.funct7 === "b0000001".U)) {io.operation := "b01010".U} //divu
    .elsewhen((io.funct3 === "b110".U) & (io.funct7 === "b0000001".U)) {io.operation := "b11100".U} //rem
    .elsewhen((io.funct3 === "b111".U) & (io.funct7 === "b0000001".U)) {io.operation := "b11011".U} //remu
  }
  .elsewhen(io.aluop === 3.U){ // 32 bit R-type
  when((io.funct3 === "b000".U) & (io.funct7 === "b0000000".U)) {io.operation := "b00000".U} //addw
  .elsewhen((io.funct3 === "b000".U) & (io.funct7 === "b0100000".U)) {io.operation := "b00010".U} //subw
  .elsewhen((io.funct3 === "b001".U) & (io.funct7 === "b0000000".U)) {io.operation := "b10011".U} //sllw
  .elsewhen((io.funct3 === "b101".U) & (io.funct7 === "b0000000".U)) {io.operation := "b10101".U} //srlw
  .elsewhen((io.funct3 === "b101".U) & (io.funct7 === "b0100000".U)) {io.operation := "b10001".U} //sraw
  .elsewhen((io.funct3 === "b000".U) & (io.funct7 === "b0000001".U)) {io.operation := "b00101".U} //mulw
  .elsewhen((io.funct3 === "b100".U) & (io.funct7 === "b0000001".U)) {io.operation := "b01001".U} //divw
  .elsewhen((io.funct3 === "b101".U) & (io.funct7 === "b0000001".U)) {io.operation := "b01100".U} //divuw
  .elsewhen((io.funct3 === "b110".U) & (io.funct7 === "b0000001".U)) {io.operation := "b11010".U} //remw
  .elsewhen((io.funct3 === "b111".U) & (io.funct7 === "b0000001".U)) {io.operation := "b11001".U} //remuw
  }

}

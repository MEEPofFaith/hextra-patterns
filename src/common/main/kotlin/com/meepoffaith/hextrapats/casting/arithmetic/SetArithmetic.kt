package com.meepoffaith.hextrapats.casting.arithmetic

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic.*
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorUnary
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.math.HexPattern
import com.meepoffaith.hextrapats.casting.arithmetic.operators.set.*
import com.meepoffaith.hextrapats.registry.HextraActions.SET_INSERT_RET
import com.meepoffaith.hextrapats.registry.HextraActions.SET_REMOVE_RET
import com.meepoffaith.hextrapats.registry.HextraIotas
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS

object SetArithmetic : Arithmetic {
    private val OPS = listOf(
        ADD,
        SUB,
        AND,
        XOR,
        ABS,
        INDEX_OF,
        APPEND,
        SET_INSERT_RET,
        REMOVE,
        SET_REMOVE_RET
    )

    override fun arithName() = "set_ops"

    override fun opTypes() = OPS

    override fun getOperator(pattern: HexPattern): Operator = when(pattern){
        ADD -> OperatorAddSets
        SUB -> OperatorSubtractSets
        AND -> OperatorIntersection
        XOR -> OperatorDisjunction
        ABS -> OperatorUnary(ALL_SETS) { iota: Iota -> DoubleIota(Operator.downcast(iota, HextraIotas.SET).iotaSet.size.toDouble()) }
        INDEX_OF -> OperatorExists
        APPEND -> OperatorInsert(false)
        SET_INSERT_RET -> OperatorInsert(true)
        REMOVE -> OperatorRemove(false)
        SET_REMOVE_RET -> OperatorRemove(true)
        else -> throw InvalidOperatorException("$pattern is not a valid operator in Arithmetic $this.")
    }
}

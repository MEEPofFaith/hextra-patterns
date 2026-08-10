package com.meepoffaith.hextrapats.casting.arithmetic

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic.*
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.math.HexPattern
import com.meepoffaith.hextrapats.casting.arithmetic.operators.set.*
import com.meepoffaith.hextrapats.init.Patterns.SET_INSERT_RET
import com.meepoffaith.hextrapats.init.Patterns.SET_REMOVE_RET

class SetArithmetic : Arithmetic {
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
        ABS -> OperatorAmount
        INDEX_OF -> OperatorExists
        APPEND -> OperatorInsert(false)
        SET_INSERT_RET -> OperatorInsert(true)
        REMOVE -> OperatorRemove(false)
        SET_REMOVE_RET -> OperatorRemove(true)
        else -> throw InvalidOperatorException("$pattern is not a valid operator in Arithmetic $this.")
    }
}

package com.meepoffaith.hextrapats.casting.arithmetic

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.math.HexPattern
import com.meepoffaith.hextrapats.casting.arithmetic.operators.bool.OperatorInRange
import com.meepoffaith.hextrapats.casting.arithmetic.operators.bool.OperatorOutRange
import com.meepoffaith.hextrapats.init.Patterns.IN_RANGE
import com.meepoffaith.hextrapats.init.Patterns.OUT_RANGE


class BoolArithmetic : Arithmetic {
    private val OPS = listOf(
        IN_RANGE,
        OUT_RANGE
    )

    override fun arithName() = "hextrapats_bool_math"

    override fun opTypes() = OPS

    override fun getOperator(pattern: HexPattern?): Operator = when(pattern){
        IN_RANGE -> OperatorInRange()
        OUT_RANGE -> OperatorOutRange()
        else -> throw InvalidOperatorException("$pattern is not a valid operator in Bool Arithmetic $this.")
    }
}

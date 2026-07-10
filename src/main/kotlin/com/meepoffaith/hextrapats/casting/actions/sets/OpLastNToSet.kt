package com.meepoffaith.hextrapats.casting.actions.sets

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.getPositiveIntUnderInclusive
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import com.meepoffaith.hextrapats.casting.iota.*
import net.minecraft.entity.Entity


object OpLastNToSet : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        var stack = image.stack.toMutableList()

        if(stack.size < 2) throw MishapNotEnoughArgs(2, stack.size)

        val yoinkCount = stack.getPositiveIntUnderInclusive(stack.size - 1, stack.size - 1, stack.size)
        val start = stack[stack.size - 2]
        val out = when(start){
            is DoubleIota -> bucketNums(stack, yoinkCount)
            is Vec3Iota -> bucketVecs(stack, yoinkCount)
            is EntityIota -> bucketEnts(stack, yoinkCount)
            else -> throw MishapInvalidIota.of(start, 1, "hextrapats:set_item")
        }

        stack = stack.subList(0, stack.size - 1 - yoinkCount)
        stack.add(out)

        val img2: CastingImage = image.withUsedOp().copy(stack = stack)
        return OperationResult(img2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE)
    }

    fun bucketNums(stack: MutableList<Iota>, yoinkCount: Int): DoubleSetIota {
        val set = DoubleSet()
        for (i in 0..<yoinkCount) {
            set.add(stack.getDouble(stack.size - 2 - i, stack.size))
        }
        return DoubleSetIota(set)
    }

    fun bucketVecs(stack: MutableList<Iota>, yoinkCount: Int): VecSetIota {
        val set = VecSet()
        for (i in 0..<yoinkCount) {
            set.add(stack.getVec3(stack.size - 2 - i, stack.size))
        }
        return VecSetIota(set)
    }

    fun bucketEnts(stack: MutableList<Iota>, yoinkCount: Int): EntitySetIota {
        val set = HashSet<Entity>()
        for (i in 0..<yoinkCount) {
            set.add(stack.getEntity(stack.size - 2 - i, stack.size))
        }
        return EntitySetIota(set)
    }
}

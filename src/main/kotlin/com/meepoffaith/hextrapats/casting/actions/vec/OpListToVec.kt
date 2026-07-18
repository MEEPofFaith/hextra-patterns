package com.meepoffaith.hextrapats.casting.actions.vec

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import net.minecraft.util.math.Vec3d

object OpListToVec : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0)
        if(list.size() != 3){
            throw MishapInvalidIota.of(args[0], 0, "hextrapats:vec_list")
        }
        val x = list.getAt(0)
        val y = list.getAt(1)
        val z = list.getAt(2)
        if(x is DoubleIota && y is DoubleIota && z is DoubleIota){
            return Vec3d(x.double, y.double, z.double).asActionResult
        }else{
            throw MishapInvalidIota.of(args[0], 0, "hextrapats:vec_list")
        }
    }
}

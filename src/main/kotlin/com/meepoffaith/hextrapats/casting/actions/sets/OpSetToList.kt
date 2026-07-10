package com.meepoffaith.hextrapats.casting.actions.sets

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import com.meepoffaith.hextrapats.casting.iota.DoubleSet
import com.meepoffaith.hextrapats.casting.iota.VecSet
import com.meepoffaith.hextrapats.util.HextraUtils.getSet
import net.minecraft.entity.Entity


object OpSetToList : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val set = args.getSet(0, argc)
        val list = mutableListOf<Iota>()
        return set.operate(
            { dSet: DoubleSet ->
                for (d in dSet) {
                    list.add(DoubleIota(d))
                }
                list.asActionResult
            }, { vSet: VecSet ->
                for (v in vSet) {
                    list.add(Vec3Iota(v))
                }
                list.asActionResult
            }, { eSet: Set<Entity> ->
                for (d in eSet) {
                    list.add(EntityIota(d))
                }
                list.asActionResult
            }
        )
    }
}

package com.meepoffaith.hextrapats.casting.actions.sets

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import com.meepoffaith.hextrapats.casting.iota.IotaSet

object OpListToSet : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val iotas = args.getList(0, argc)
        val set = IotaSet()
        for(iota in iotas){
            if(IotaSet.checkType(iota)){
                set.add(iota)
            }else{
                throw MishapInvalidIota.of(args[0], 0, "hextrapats:set_item_list")
            }
        }
        return set.asActionResult()
    }
}

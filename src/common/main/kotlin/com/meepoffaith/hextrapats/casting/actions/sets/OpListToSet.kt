package com.meepoffaith.hextrapats.casting.actions.sets

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import com.meepoffaith.hextrapats.casting.iota.IotaMap
import com.meepoffaith.hextrapats.casting.iota.SetIota

object OpListToSet : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val iotas = args.getList(0, argc)
        val map = IotaMap()
        for(iota in iotas){
            if(IotaMap.checkType(iota)){
                map.addIota(iota)
            }else{
                throw MishapInvalidIota.of(iotas, 0, "hextrapats:set_item_list")
            }
        }
        return map.asActionResult()
    }
}

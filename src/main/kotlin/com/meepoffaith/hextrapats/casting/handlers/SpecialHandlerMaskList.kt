package com.meepoffaith.hextrapats.casting.handlers

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.math.HexAngle
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import at.petrak.hexcasting.api.utils.lightPurple
import com.meepoffaith.hextrapats.init.SpecialHandlers
import com.meepoffaith.hextrapats.util.HextraUtils
import it.unimi.dsi.fastutil.booleans.BooleanArrayList
import it.unimi.dsi.fastutil.booleans.BooleanList
import net.minecraft.text.Text

class SpecialHandlerMaskList(val mask: BooleanList) : SpecialHandler {
    override fun act(): Action = InnerAction(mask)

    override fun getName(): Text {
        val fingerprint = mask.map { if (it) 'v' else '-' }.joinToString("")
        return HextraUtils.specialHandlerLang(SpecialHandlers.MASK_LIST).asTranslatedComponent(fingerprint).lightPurple
    }

    class InnerAction(val mask: BooleanList) : ConstMediaAction {
        override val argc = mask.size
        override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
            val keep = ArrayList<Iota>(this.mask.size)
            val out = ArrayList<Iota>(this.mask.size)
            for((i, include) in this.mask.withIndex()){
                if(include) {
                    out.add(args[i])
                }else{
                    keep.add(args[i])
                }
            }
            keep.add(ListIota(out))
            return keep
        }
    }

    class Factory : SpecialHandler.Factory<SpecialHandlerMaskList> {
        override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): SpecialHandlerMaskList? {
            val sig = pattern.anglesSignature()
            if(sig.startsWith("ewdqdwedww")){
                val directions = pattern.directions().drop(10)
                val flatDir = directions.first()

                // Copied from SpecialHandlerMask
                val mask = BooleanArrayList()
                var i = 1
                while(i < directions.size){
                    // Angle with respect to the *start direction*
                    val angle = directions[i].angleFrom(flatDir);
                    if(angle == HexAngle.FORWARD){
                        mask.add(false)
                        i++
                        continue
                    }
                    if(i >= directions.size - 1){
                        // then we're out of angles!
                        return null
                    }
                    val angle2 = directions[i + 1].angleFrom(flatDir);
                    if(angle == HexAngle.LEFT && angle2 == HexAngle.RIGHT){
                        mask.add(true)
                        // skip both segments of the dip
                        i += 2
                        continue
                    }
                    return null
                }
                mask.reverse() // Since this is drawn from back-to-front
                return SpecialHandlerMaskList(mask)
            }
            return null
        }
    }
}

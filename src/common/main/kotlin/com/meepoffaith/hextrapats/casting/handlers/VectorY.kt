package com.meepoffaith.hextrapats.casting.handlers

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import at.petrak.hexcasting.api.utils.lightPurple
import com.meepoffaith.hextrapats.init.SpecialHandlers
import com.meepoffaith.hextrapats.util.HextraUtils
import com.meepoffaith.hextrapats.util.HextraUtils.numericalReflection
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d

class VectorY(val y: Double) : SpecialHandler{
    override fun act(): Action{
        return InnerAction(y)
    }

    override fun getName(): Text{
        val num = Action.DOUBLE_FORMATTER.format(y)
        return HextraUtils.specialHandlerLang(SpecialHandlers.VEC_Y).asTranslatedComponent(num).lightPurple
    }

    class InnerAction(val y: Double) : ConstMediaAction{
        override val argc = 0
        override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
            return Vec3d(0.0, y, 0.0).asActionResult
        }
    }

    class Factory : SpecialHandler.Factory<VectorY>{
        override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): VectorY? {
            val sig = pattern.anglesSignature()
            if(sig.startsWith("weaqaa") || sig.startsWith("wqdedd")){
                val num = numericalReflection(sig.substring(6)) *
                        (if (sig.startsWith("wqdedd")) -1.0 else 1.0)
                return VectorY(num)
            } else {
                return null
            }
        }
    }
}

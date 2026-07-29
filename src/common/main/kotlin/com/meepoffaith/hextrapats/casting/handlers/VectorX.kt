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

class VectorX(val x: Double) : SpecialHandler{
    override fun act(): Action{
        return InnerAction(x)
    }

    override fun getName(): Text{
        val num = Action.DOUBLE_FORMATTER.format(x)
        return HextraUtils.specialHandlerLang(SpecialHandlers.VEC_X).asTranslatedComponent(num).lightPurple
    }

    class InnerAction(val x: Double) : ConstMediaAction{
        override val argc = 0
        override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
            return Vec3d(x, 0.0, 0.0).asActionResult
        }
    }

    class Factory : SpecialHandler.Factory<VectorX>{
        override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): VectorX? {
            val sig = pattern.anglesSignature()
            if(sig.startsWith("aeaqaa") || sig.startsWith("aqdedd")){
                val num = numericalReflection(sig.substring(6)) *
                        (if (sig.startsWith("aqdedd")) -1.0 else 1.0)
                return VectorX(num)
            } else {
                return null
            }
        }
    }
}

package com.meepoffaith.hextrapats.casting.handlers

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getNumOrVec
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import at.petrak.hexcasting.api.utils.lightPurple
import com.meepoffaith.hextrapats.init.SpecialHandlers
import com.meepoffaith.hextrapats.util.HextraUtils
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d
import kotlin.math.pow

class ScientificExponent(val exp: Int) : SpecialHandler{
    override fun act(): Action? {
        return InnerAction(exp)
    }

    override fun getName(): Text? {
        val num = Action.DOUBLE_FORMATTER.format(exp)
        return HextraUtils.specialHandlerLang(SpecialHandlers.SCI_EXP).asTranslatedComponent(num).lightPurple
    }

    class InnerAction(val exp: Int) : ConstMediaAction{
        override val argc = 1
        override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
            val arg = args.getNumOrVec(0, argc)
            val pow = 10.0.pow(exp)
            return arg.map(
                { num -> (num * pow).asActionResult },
                { vec -> Vec3d(vec.x * pow, vec.y * pow, vec.z * pow).asActionResult }
            )
        }
    }

    class Factory : SpecialHandler.Factory<ScientificExponent> {
        override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): ScientificExponent? {
            val sig = pattern.anglesSignature()
            if(sig.startsWith("waqe") || sig.startsWith("wdeq")){
                val divide = sig.startsWith("wdeq")
                val chars = sig.substring(4).toCharArray()
                var exponent = 1
                for(i in chars.indices){ //Code based on Sekhmet from Overevaluate
                    if(chars[i] != "qe"[(i + (if (divide) 1 else 0)) % 2]){
                        return null
                    }
                    exponent++
                }
                return ScientificExponent(exponent * (if (divide) -1 else 1))
            }
            return null
        }
    }
}

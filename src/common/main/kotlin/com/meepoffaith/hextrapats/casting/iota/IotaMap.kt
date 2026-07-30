package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.*
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import com.meepoffaith.hextrapats.util.MathUtils

class IotaMap : HashMap<Int, Iota> {
    constructor(): super()
    constructor(map: IotaMap) : super(map)
    constructor(list: List<Iota>, argcOrIdx: Int = 0) : super(){
        for(i in 0..list.lastIndex){
            addIota(list[i], if (argcOrIdx > 0) argcOrIdx - (i + 1) else argcOrIdx)
        }
    }

    fun copy(): IotaMap{
        return IotaMap(this)
    }

    fun addIota(iota: Iota): Boolean{
        val input = coerceIota(iota)
        return put(input.hashCode(), input) == null
    }

    fun addIota(iota: Iota, reversedIdx: Int): Boolean{
        if(!checkType(iota)) {
            if(reversedIdx >= 0) {
                throw MishapInvalidIota.of(iota, reversedIdx, "hextrapats:set_item")
            }else{
                throw MishapInvalidIota.of(iota, -reversedIdx + 1, "hextrapats:list_in_list_to_set")
            }
        }
        return addIota(iota)
    }

    fun containsIota(iota: Iota): Boolean{
        val input = coerceIota(iota)
        return containsKey(input.hashCode())
    }

    fun removeIota(iota: Iota): Boolean{
        val input = coerceIota(iota)
        return remove(input.hashCode()) != null
    }

    fun removeAll(map: IotaMap){
        map.keys.forEach{ remove(it) }
    }

    fun asActionResult(): List<Iota>{
        return listOf(SetIota(this))
    }

    override fun hashCode(): Int {
        var hashCode = 2 // Hopefully, starting on 2 instead of 1 is enough to differentiate from a list.
        for(key in keys){
            hashCode *= 31
            hashCode += key
        }
        return hashCode
    }

    companion object {
        /** Iota types disallowed from being input into an iota map. */
        val DISALLOWED_TYPES = mutableListOf<IotaType<*>>(ListIota.TYPE, SetIota.TYPE)

        fun checkType(iota: Iota): Boolean{
            return !DISALLOWED_TYPES.contains(iota.type)
        }

        fun coerceIota(iota: Iota): Iota{
            return when(iota){
                is DoubleIota -> DoubleIota(MathUtils.roundToTolerance(iota.double))
                is Vec3Iota -> Vec3Iota(MathUtils.roundToTolerance(iota.vec3))
                else -> iota
            }
        }
    }
}

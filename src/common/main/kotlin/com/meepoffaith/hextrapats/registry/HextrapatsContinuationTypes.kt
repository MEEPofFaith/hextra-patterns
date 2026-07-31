package com.meepoffaith.hextrapats.registry

import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexContinuationTypes
import com.meepoffaith.hextrapats.casting.eval.vm.FrameForEachIndex
import com.meepoffaith.hextrapats.casting.eval.vm.FrameMainForEach

object HextrapatsContinuationTypes : HextrapatsRegistrar<ContinuationFrame.Type<*>>(
    HexRegistries.CONTINUATION_TYPE,
    { HexContinuationTypes.REGISTRY }
) {
    val FOREACH_INDEX = make("foreach_index", FrameForEachIndex.TYPE)
    val MAIN_FOREACH = make("main_foreach", FrameMainForEach.TYPE)

    private fun <U: ContinuationFrame, T: ContinuationFrame.Type<U>> make(name: String, continuation: T) : T{
        register(name) { continuation }
        return continuation
    }
}

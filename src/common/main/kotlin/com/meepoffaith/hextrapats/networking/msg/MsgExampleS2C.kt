package com.meepoffaith.hextrapats.networking.msg

@JvmRecord
data class MsgExampleS2C(val payload: Int) : HextrapatsMessageS2C {
   companion object : HextrapatsMessageCompanion<MsgExampleS2C> {
       override val type = MsgExampleS2C::class.java
   }
}

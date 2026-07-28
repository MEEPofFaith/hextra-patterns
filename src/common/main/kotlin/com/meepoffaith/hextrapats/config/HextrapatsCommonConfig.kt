package com.meepoffaith.hextrapats.config

import com.meepoffaith.hextrapats.Hextrapats
import me.fzzyhmstrs.fzzy_config.config.Config

// guide: https://moddedmc.wiki/en/project/fzzy-config/latest/docs/config-design/New-Configs#2-config-creation
class HextrapatsCommonConfig : Config(Hextrapats.id("common_config")) {

    var testValue = 1.5

}
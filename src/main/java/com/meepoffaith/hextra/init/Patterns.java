package com.meepoffaith.hextra.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;

import com.meepoffaith.hextra.casting.actions.MathMax;
import com.meepoffaith.hextra.casting.actions.MathMin;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.meepoffaith.hextra.HextraPatterns;

public class Patterns{
    public static void init(){
        register("math_min", "awddee", HexDir.SOUTH_EAST, new MathMin());
        register("math_max", "awddae", HexDir.SOUTH_EAST, new MathMax());
    }

    private static void register(
        String name,
        String signature,
        HexDir startDir,
        Action action
    ) {
        Registry.register(HexActions.REGISTRY, new Identifier(HextraPatterns.MOD_ID, name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }
}

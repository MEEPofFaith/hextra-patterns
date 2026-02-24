package com.meepoffaith.hextra.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;

import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.meepoffaith.hextra.casting.actions.VecActions.VecNegOne;
import com.meepoffaith.hextra.casting.actions.VecActions.VecOne;
import com.meepoffaith.hextra.casting.handlers.XVectorLiteral.XVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.YVectorLiteral.YVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.ZVectorLiteral.ZVectorLiteralFactory;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.meepoffaith.hextra.HextraPatterns;

public class Patterns{
    public static void init(){
        //Come on, Elise!
        register("haha_ha_one", "qqqqqeq", HexDir.WEST, new VecOne());
        register("eno_ah_ahah", "eeeeeqq", HexDir.EAST, new VecNegOne());

        registerSpecialHandler("scaled_vec_x", new XVectorLiteralFactory());
        registerSpecialHandler("scaled_vec_y", new YVectorLiteralFactory());
        registerSpecialHandler("scaled_vec_z", new ZVectorLiteralFactory());
    }

    private static void register(
        String name,
        String signature,
        HexDir startDir,
        Action action
    ) {
        Registry.register(HexActions.REGISTRY, new Identifier(HextraPatterns.MOD_ID, name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }

    private static void registerSpecialHandler(
        String name,
        SpecialHandler.Factory<?> handler
    ) {
        Registry.register(IXplatAbstractions.INSTANCE.getSpecialHandlerRegistry(), new Identifier(HextraPatterns.MOD_ID, name), handler);
    }
}

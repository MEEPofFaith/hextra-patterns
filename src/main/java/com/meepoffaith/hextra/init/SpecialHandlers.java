package com.meepoffaith.hextra.init;

import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.meepoffaith.hextra.HextraPatterns;
import com.meepoffaith.hextra.casting.handlers.AllVectorLiteral.AllVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.XVectorLiteral.XVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.YVectorLiteral.YVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.ZVectorLiteral.ZVectorLiteralFactory;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SpecialHandlers{
    public static void init(){
        registerSpecialHandler("scaled_vec_x", new XVectorLiteralFactory());
        registerSpecialHandler("scaled_vec_y", new YVectorLiteralFactory());
        registerSpecialHandler("scaled_vec_z", new ZVectorLiteralFactory());
        registerSpecialHandler("scaled_vec_all", new AllVectorLiteralFactory());
    }

    private static void registerSpecialHandler(
        String name,
        SpecialHandler.Factory<?> handler
    ) {
        Registry.register(IXplatAbstractions.INSTANCE.getSpecialHandlerRegistry(), new Identifier(HextraPatterns.MOD_ID, name), handler);
    }
}

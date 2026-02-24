package com.meepoffaith.hextra.init;

import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler.Factory;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.meepoffaith.hextra.HextraPatterns;
import com.meepoffaith.hextra.casting.handlers.AllVectorLiteral.AllVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.XVectorLiteral.XVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.YVectorLiteral.YVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.ZVectorLiteral.ZVectorLiteralFactory;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SpecialHandlers{
    public static Factory<?> SCALED_VEC_X = new XVectorLiteralFactory();
    public static Factory<?> SCALED_VEC_Y = new YVectorLiteralFactory();
    public static Factory<?> SCALED_VEC_Z = new ZVectorLiteralFactory();
    public static Factory<?> SCALED_VEC_ALL = new AllVectorLiteralFactory();
}

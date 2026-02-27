package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.castables.SpecialHandler.Factory;
import com.meepoffaith.hextrapats.casting.handlers.AllVectorLiteral.AllVectorLiteralFactory;
import com.meepoffaith.hextrapats.casting.handlers.XVectorLiteral.XVectorLiteralFactory;
import com.meepoffaith.hextrapats.casting.handlers.YVectorLiteral.YVectorLiteralFactory;
import com.meepoffaith.hextrapats.casting.handlers.ZVectorLiteral.ZVectorLiteralFactory;

public class SpecialHandlers{
    public static Factory<?> SCALED_VEC_X = new XVectorLiteralFactory();
    public static Factory<?> SCALED_VEC_Y = new YVectorLiteralFactory();
    public static Factory<?> SCALED_VEC_Z = new ZVectorLiteralFactory();
    public static Factory<?> SCALED_VEC_ALL = new AllVectorLiteralFactory();
}

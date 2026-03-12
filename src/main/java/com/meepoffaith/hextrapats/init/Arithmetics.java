package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexArithmetics;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.arithmetic.BoolArithmetic;
import com.meepoffaith.hextrapats.casting.arithmetic.NumArithmetic;
import com.meepoffaith.hextrapats.casting.arithmetic.Vec3Arithmetic;
import com.meepoffaith.hextrapats.casting.arithmetic.Vec3BoolArithmetic;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Arithmetics{
    public static HexPattern
        ROT_ABOUT_X = Patterns.ROT_ABOUT_X.prototype(),
        ROT_ABOUT_Y = Patterns.ROT_ABOUT_Y.prototype(),
        ROT_ABOUT_Z = Patterns.ROT_ABOUT_Z.prototype(),
        CONSTRUCT_ABOUT_X = Patterns.CONSTRUCT_ABOUT_X.prototype(),
        CONSTRUCT_ABOUT_Y = Patterns.CONSTRUCT_ABOUT_Y.prototype(),
        CONSTRUCT_ABOUT_Z = Patterns.CONSTRUCT_ABOUT_Z.prototype(),
        NORMALIZE = Patterns.NORMALIZE.prototype(),
        LEN_EQ = Patterns.LEN_EQ.prototype(),
        LEN_NEQ = Patterns.LEN_NEQ.prototype(),
        IN_RANGE = Patterns.IN_RANGE.prototype(),
        OUT_RANGE = Patterns.OUT_RANGE.prototype(),
        INVERT = Patterns.INVERT.prototype(),
        INCREMENT = Patterns.INCREMENT.prototype(),
        DECREMENT = Patterns.DECREMENT.prototype();

    public static void init(){
        registerArithmetic("bool", new BoolArithmetic());
        registerArithmetic("vec3bool", new Vec3BoolArithmetic());
        registerArithmetic("nummath", new NumArithmetic());
        registerArithmetic("vec3math", new Vec3Arithmetic());
    }

    private static void registerArithmetic(
        String name,
        Arithmetic a
    ){
        Registry.register(HexArithmetics.REGISTRY,  new Identifier(HextraPatterns.MOD_ID, name), a);
    }
}

package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.common.lib.hex.HexArithmetics;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.arithmetic.Vec3Arithmetic;
import com.meepoffaith.hextrapats.casting.arithmetic.Vec3BoolArithmetic;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Arithmetics{
    public static void init(){
        registerArithmetic("vec3bool", new Vec3BoolArithmetic());
        registerArithmetic("vec3math", new Vec3Arithmetic());
    }

    private static void registerArithmetic(
        String name,
        Arithmetic a
    ){
        Registry.register(HexArithmetics.REGISTRY,  new Identifier(HextraPatterns.MOD_ID, name), a);
    }
}

package com.meepoffaith.hextra.init;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexArithmetics;
import com.meepoffaith.hextra.HextraPatterns;
import com.meepoffaith.hextra.casting.arithmetic.Vec3Arithmetic;
import com.meepoffaith.hextra.casting.arithmetic.Vec3BoolArithmetic;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Arithmetics{
    public static HexPattern ROT_ABOUT_X = HexPattern.fromAngles("aaqqqqqea", HexDir.SOUTH_WEST);
    public static HexPattern ROT_ABOUT_Y = HexPattern.fromAngles("aaqqqqqew", HexDir.SOUTH_WEST);
    public static HexPattern ROT_ABOUT_Z = HexPattern.fromAngles("aaqqqqqed", HexDir.SOUTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_X = HexPattern.fromAngles("daqqqqqea", HexDir.NORTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_Y = HexPattern.fromAngles("daqqqqqew", HexDir.NORTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_Z = HexPattern.fromAngles("daqqqqqed", HexDir.NORTH_WEST);
    public static HexPattern NORMALIZE = HexPattern.fromAngles("eeeeedww", HexDir.SOUTH_WEST);

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

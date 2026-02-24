package com.meepoffaith.hextra.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.OperationAction;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;

import at.petrak.hexcasting.common.lib.hex.HexArithmetics;
import at.petrak.hexcasting.xplat.IXplatAbstractions;

import com.meepoffaith.hextra.HextraPatterns;
import com.meepoffaith.hextra.casting.actions.MathActions.DegRad;
import com.meepoffaith.hextra.casting.actions.MathActions.RadDeg;
import com.meepoffaith.hextra.casting.actions.VecActions.Normalize;
import com.meepoffaith.hextra.casting.actions.VecActions.VecNegOne;
import com.meepoffaith.hextra.casting.actions.VecActions.VecOne;
import com.meepoffaith.hextra.casting.arithmetic.Vec3Arithmetic;
import com.meepoffaith.hextra.casting.arithmetic.Vec3BoolArithmetic;
import com.meepoffaith.hextra.casting.handlers.AllVectorLiteral.AllVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.XVectorLiteral.XVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.YVectorLiteral.YVectorLiteralFactory;
import com.meepoffaith.hextra.casting.handlers.ZVectorLiteral.ZVectorLiteralFactory;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.meepoffaith.hextra.init.Arithmetics.*;

public class Patterns{
    public static void init(){
        register("deg_to_rad", "qqqqqdwdq", HexDir.WEST, new DegRad());
        register("rad_to_deg", "qdwdqqqqq", HexDir.NORTH_EAST, new RadDeg());

        //Come on, Elise!
        register("haha_ha_one", "qqqqqeq", HexDir.NORTH_WEST, new VecOne());
        register("eno_ah_ahah", "eeeeeqq", HexDir.SOUTH_WEST, new VecNegOne());

        register("normalize", "eeeeedww", HexDir.SOUTH_WEST, new Normalize());

        register("rot_about_x", ROT_ABOUT_X);
        register("rot_about_y", ROT_ABOUT_Y);
        register("rot_about_z", ROT_ABOUT_Z);
    }

    private static void register(
        String name,
        String signature,
        HexDir startDir,
        Action action
    ) {
        Registry.register(HexActions.REGISTRY, new Identifier(HextraPatterns.MOD_ID, name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }

    private static void register(
        String name,
        HexPattern pattern
    ) {
        Registry.register(HexActions.REGISTRY, new Identifier(HextraPatterns.MOD_ID, name), new ActionRegistryEntry(pattern, new OperationAction(pattern)));
    }
}

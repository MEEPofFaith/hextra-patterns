package com.meepoffaith.hextra.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.OperationAction;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import com.meepoffaith.hextra.HextraPatterns;
import com.meepoffaith.hextra.casting.actions.MathActions.DegRad;
import com.meepoffaith.hextra.casting.actions.MathActions.RadDeg;
import com.meepoffaith.hextra.casting.actions.VecActions.VecNegOne;
import com.meepoffaith.hextra.casting.actions.VecActions.VecOne;
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

        register("rot_about_x", ROT_ABOUT_X);
        register("rot_about_y", ROT_ABOUT_Y);
        register("rot_about_z", ROT_ABOUT_Z);
        register("cons_about_x", CONSTRUCT_ABOUT_X);
        register("cons_about_y", CONSTRUCT_ABOUT_Y);
        register("cons_about_z", CONSTRUCT_ABOUT_Z);
        register("normalize", NORMALIZE);
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

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

public class Patterns{
    public static HexPattern ROT_ABOUT_X = register("rot_about_x", "aaqqqqqea", HexDir.SOUTH_WEST);
    public static HexPattern ROT_ABOUT_Y = register("rot_about_y", "aaqqqqqew", HexDir.SOUTH_WEST);
    public static HexPattern ROT_ABOUT_Z = register("rot_about_z", "aaqqqqqed", HexDir.SOUTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_X = register("cons_about_x", "daqqqqqea", HexDir.NORTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_Y = register("cons_about_y", "daqqqqqew", HexDir.NORTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_Z = register("cons_about_z", "daqqqqqed", HexDir.NORTH_WEST);
    public static HexPattern NORMALIZE = register("normalize", "eeeeedww", HexDir.SOUTH_WEST);

    public static void init(){
        register("deg_to_rad", "qqqqqdwdq", HexDir.WEST, new DegRad());
        register("rad_to_deg", "qdwdqqqqq", HexDir.NORTH_EAST, new RadDeg());

        //Come on, Elise!
        register("haha_ha_one", "qqqqqeq", HexDir.NORTH_WEST, new VecOne());
        register("eno_ah_ahah", "eeeeeqq", HexDir.SOUTH_WEST, new VecNegOne());
    }

    private static void register(
        String name,
        String signature,
        HexDir startDir,
        Action action
    ) {
        Registry.register(HexActions.REGISTRY, new Identifier(HextraPatterns.MOD_ID, name), new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action));
    }

    private static HexPattern register(
        String name,
        String signature,
        HexDir startDir
    ) {
        HexPattern pattern = HexPattern.fromAngles(signature, startDir);
        Registry.register(HexActions.REGISTRY, new Identifier(HextraPatterns.MOD_ID, name), new ActionRegistryEntry(pattern, new OperationAction(pattern)));
        return pattern;
    }
}

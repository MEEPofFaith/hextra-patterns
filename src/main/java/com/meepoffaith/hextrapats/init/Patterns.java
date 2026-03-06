package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.OperationAction;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.actions.OpDegRad;
import com.meepoffaith.hextrapats.casting.actions.OpRadDeg;
import com.meepoffaith.hextrapats.casting.actions.OpSplitList;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static com.meepoffaith.hextrapats.init.SpecialHandlers.*;

public class Patterns{
    public static HexPattern ROT_ABOUT_X = register("rot_about_x", "aaqqqqqea", HexDir.SOUTH_WEST);
    public static HexPattern ROT_ABOUT_Y = register("rot_about_y", "aaqqqqqew", HexDir.SOUTH_WEST);
    public static HexPattern ROT_ABOUT_Z = register("rot_about_z", "aaqqqqqed", HexDir.SOUTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_X = register("cons_about_x", "daqqqqqea", HexDir.NORTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_Y = register("cons_about_y", "daqqqqqew", HexDir.NORTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_Z = register("cons_about_z", "daqqqqqed", HexDir.NORTH_WEST);
    public static HexPattern NORMALIZE = register("normalize", "eeeeedww", HexDir.SOUTH_WEST);
    public static ActionRegistryEntry LEN_EQ = registerEntry("len_eq", "adqqaqw", HexDir.EAST);
    public static ActionRegistryEntry LEN_NEQ = registerEntry("len_neq", "daeedew", HexDir.EAST);
    public static ActionRegistryEntry IN_RANGE = registerEntry("in_range", "qqqq", HexDir.SOUTH_WEST);
    public static ActionRegistryEntry OUT_RANGE = registerEntry("out_range", "eaae", HexDir.SOUTH_EAST);

    public static void init(){
        register("deg_to_rad", "qqqqqdwdq", HexDir.WEST, new OpDegRad());
        register("rad_to_deg", "qdwdqqqqq", HexDir.NORTH_EAST, new OpRadDeg());

        //Come on, Elise!
        register("haha_ha_one", "qqqqqeq", HexDir.NORTH_WEST, Action.makeConstantOp(new Vec3Iota(new Vec3d(1.0, 1.0, 1.0))));
        register("eno_ah_ahah", "eeeeeqq", HexDir.SOUTH_WEST, Action.makeConstantOp(new Vec3Iota(new Vec3d(-1.0, -1.0, -1.0))));

        register("split_list", "wdedqqa", HexDir.EAST, new OpSplitList());

        registerSpecialHandler("scaled_vec_x", SCALED_VEC_X);
        registerSpecialHandler("scaled_vec_y", SCALED_VEC_Y);
        registerSpecialHandler("scaled_vec_z", SCALED_VEC_Z);
        registerSpecialHandler("scaled_vec_all", SCALED_VEC_ALL);
        registerSpecialHandler("retained_comparison", RETAINED_COMPARISON);
        registerSpecialHandler("scientific_exp", SCIENTIFIC_EXPONENT);
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

    private static ActionRegistryEntry registerEntry(
        String name,
        String signature,
        HexDir startDir
    ) {
        HexPattern pattern = HexPattern.fromAngles(signature, startDir);
        ActionRegistryEntry entry = new ActionRegistryEntry(pattern, new OperationAction(pattern));
        Registry.register(HexActions.REGISTRY, new Identifier(HextraPatterns.MOD_ID, name), entry);
        return entry;
    }

    private static void registerSpecialHandler(
        String name,
        SpecialHandler.Factory<?> handler
    ) {
        Registry.register(IXplatAbstractions.INSTANCE.getSpecialHandlerRegistry(), new Identifier(HextraPatterns.MOD_ID, name), handler);
    }
}

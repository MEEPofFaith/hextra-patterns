package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.OperationAction;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.actions.NoConsumeOperationAction;
import com.meepoffaith.hextrapats.casting.actions.lists.*;
import com.meepoffaith.hextrapats.casting.actions.logic.OpNoConsumeEquality;
import com.meepoffaith.hextrapats.casting.actions.math.*;
import com.meepoffaith.hextrapats.casting.actions.vecmath.*;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static com.meepoffaith.hextrapats.init.SpecialHandlers.*;

public class Patterns{
    public static ActionRegistryEntry ROT_ABOUT_X = registerEntry("rot_about_x", "aaqqqqqea", HexDir.SOUTH_WEST);
    public static ActionRegistryEntry ROT_ABOUT_Y = registerEntry("rot_about_y", "aaqqqqqew", HexDir.SOUTH_WEST);
    public static ActionRegistryEntry ROT_ABOUT_Z = registerEntry("rot_about_z", "aaqqqqqed", HexDir.SOUTH_WEST);
    public static ActionRegistryEntry CONSTRUCT_ABOUT_X = registerEntry("cons_about_x", "daqqqqqea", HexDir.NORTH_WEST);
    public static ActionRegistryEntry CONSTRUCT_ABOUT_Y = registerEntry("cons_about_y", "daqqqqqew", HexDir.NORTH_WEST);
    public static ActionRegistryEntry CONSTRUCT_ABOUT_Z = registerEntry("cons_about_z", "daqqqqqed", HexDir.NORTH_WEST);
    public static ActionRegistryEntry NORMALIZE = registerEntry("normalize", "eeeeedww", HexDir.SOUTH_WEST);
    public static ActionRegistryEntry LEN_EQ = registerEntry("len_eq", "adqqaqw", HexDir.EAST);
    public static ActionRegistryEntry LEN_NEQ = registerEntry("len_neq", "daeedew", HexDir.EAST);
    public static ActionRegistryEntry IN_RANGE = registerEntry("in_range", "qqqq", HexDir.SOUTH_WEST);
    public static ActionRegistryEntry OUT_RANGE = registerEntry("out_range", "eaae", HexDir.SOUTH_EAST);
    public static ActionRegistryEntry INVERT = registerEntry("invert", "waqawqa", HexDir.SOUTH_WEST);
    public static ActionRegistryEntry INCREMENT = registerEntry("increment", "waawawaaw", HexDir.NORTH_EAST);
    public static ActionRegistryEntry DECREMENT = registerEntry("decrement", "wddwdwddw", HexDir.NORTH_WEST);

    public static void init(){
        register("deg_to_rad", "qqqqqdwdq", HexDir.WEST, new OpDegRad());
        register("rad_to_deg", "qdwdqqqqq", HexDir.NORTH_EAST, new OpRadDeg());
        register("rand_zero", "dedqeqqq", HexDir.EAST, new OpRandRange());
        register("rand_range", "eeeqeqqq", HexDir.SOUTH_WEST, new OpRandZero());
        register("linear_approach", "wwadeeed", HexDir.EAST, new OpApproach());
        register("angle_dist", "dqqqqd", HexDir.SOUTH_EAST, new OpAngleDist());
        register("angle_approach", "deeeea", HexDir.EAST, new OpAngleApproach());

        register("rand_vec", "eeeeeqeqqq", HexDir.EAST, new OpRandVec());
        register("vec_dist", "aqqqqqeqeeeeed", HexDir.EAST, new OpVecDist());
        register("vec_approach", "aqqqqqeadeeed", HexDir.EAST, new OpVecApproach());
        register("from_polar", "eqqadaqa", HexDir.EAST, new OpFromPolar());
        register("to_polar", "qedadeed", HexDir.EAST, new OpToPolar());

        register("scronglwfijspoivjqwofklcrvewb", "ddedqdaqwdwaqawdwqaqww", HexDir.EAST, new OpShuffle());

        //Come on, Elise!
        register("haha_ha_one", "qqqqqeq", HexDir.NORTH_WEST, Action.makeConstantOp(new Vec3Iota(new Vec3d(1.0, 1.0, 1.0))));
        register("eno_ah_ahah", "eeeeeqq", HexDir.SOUTH_WEST, Action.makeConstantOp(new Vec3Iota(new Vec3d(-1.0, -1.0, -1.0))));

        register("split_list", "wdedqqa", HexDir.EAST, new OpSplitList());
        register("del_element/first", "dedwqaeaqa", HexDir.NORTH_EAST, new OpDelElement());
        register("del_element/all", "dedwqaeaqaw", HexDir.NORTH_EAST, new OpDelAllElement());
        register("swindle_list", "dqdeqaawddea", HexDir.WEST, new OpListSwindle());

        registerNoConsumeOp("nocon/greater", "ddwe", HexDir.WEST, Arithmetic.GREATER);
        registerNoConsumeOp("nocon/less", "ddeq", HexDir.WEST, Arithmetic.LESS);
        registerNoConsumeOp("nocon/greater_eq", "ddwee", HexDir.WEST, Arithmetic.GREATER_EQ);
        registerNoConsumeOp("nocon/less_eq", "ddeqq", HexDir.WEST, Arithmetic.LESS_EQ);
        registerNoConsumeOp("nocon/len_eq", "ddqadqqaqw", HexDir.WEST, Arithmetics.LEN_EQ);
        registerNoConsumeOp("nocon/len_neq", "ddqdaeedew", HexDir.WEST, Arithmetics.LEN_NEQ);
        register("nocon/eq", "ddqad", HexDir.WEST, new OpNoConsumeEquality(false));
        register("nocon/neq", "ddqda", HexDir.WEST, new OpNoConsumeEquality(true));

        registerSpecialHandler("scaled_vec_x", SCALED_VEC_X);
        registerSpecialHandler("scaled_vec_y", SCALED_VEC_Y);
        registerSpecialHandler("scaled_vec_z", SCALED_VEC_Z);
        registerSpecialHandler("scaled_vec_all", SCALED_VEC_ALL);
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

    private static void registerNoConsumeOp(
        String name,
        String signature,
        HexDir startDir,
        HexPattern copied
    ){
        Registry.register(HexActions.REGISTRY, new Identifier(HextraPatterns.MOD_ID, name),
            new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), new NoConsumeOperationAction(copied))
        );
    }
}

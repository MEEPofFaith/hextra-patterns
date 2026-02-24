package com.meepoffaith.hextra.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3;
import static com.meepoffaith.hextra.init.Arithmetics.*;

public class Vec3Arithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        ROT_ABOUT_X,
        ROT_ABOUT_Y,
        ROT_ABOUT_Z
    );

    @Override
    public String arithName(){
        return "hextra_vec3_math";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){
        //Vec3d already has rotate functions, but for some reason they take in a float for the angle.
        if(pattern.equals(ROT_ABOUT_X)){
            return make2((v, x) -> {
                double c = Math.cos(x);
                double s = Math.sin(x);
                return new Vec3d(v.x, c*v.y + s*v.z, c*v.z - s*v.y);
            });
        }else if(pattern.equals(ROT_ABOUT_Y)){
            return make2((v, x) -> {
                double c = Math.cos(x);
                double s = Math.sin(x);
                return new Vec3d(c*v.x + s*v.z, v.y, c*v.z - s * v.x);
            });
        }else if(pattern.equals(ROT_ABOUT_Z)){
            return make2((v, x) -> {
                double c = Math.cos(x);
                double s = Math.sin(x);
                return new Vec3d(c*v.x + s*v.y, c*v.y - s*v.x, v.z);
            });
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Vec3 Arithmetic " + this);
        }
    }

    private OperatorBinary make2(Vec3dDoubleFunction op){
        return new OperatorBinary(IotaMultiPredicate.pair(IotaPredicate.ofType(VEC3), IotaPredicate.ofType(DOUBLE)),
            (i, j) -> new Vec3Iota(op.apply(Operator.downcast(i, VEC3).getVec3(), Operator.downcast(j, DOUBLE).getDouble()))
        );
    }

    private interface Vec3dDoubleFunction{
        Vec3d apply(Vec3d v, double x);
    }
}

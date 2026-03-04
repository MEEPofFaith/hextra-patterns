package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.OperatorQuadary;
import com.meepoffaith.hextrapats.util.HextraUtils;
import com.meepoffaith.hextrapats.util.QuadIotaPredicte;
import com.meepoffaith.hextrapats.util.generics.Func2to1;
import com.meepoffaith.hextrapats.util.generics.Func4to1;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3;
import static com.meepoffaith.hextrapats.init.Arithmetics.*;

public class Vec3BoolArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        GREATER,
        LESS,
        GREATER_EQ,
        LESS_EQ,
        LEN_EQ,
        LEN_NEQ,
        IN_RANGE,
        OUT_RANGE
    );

    @Override
    public String arithName(){
        return "hextrapats_vec3_bool_math";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){ // Switchelss behavior :pensive:
        if(pattern.equals(GREATER)){
            return makeComp((a, b) -> a.length() > b.length());
        }else if(pattern.equals(LESS)){
            return makeComp((a, b) -> a.length() < b.length());
        }else if(pattern.equals(GREATER_EQ)){
            return makeComp((a, b) -> HextraUtils.greaterEq(a.length(), b.length()));
        }else if(pattern.equals(LESS_EQ)){
            return makeComp((a, b) -> HextraUtils.lessEq(a.length(), b.length()));
        }else if(pattern.equals(LEN_EQ)){
            return makeComp((a, b) -> DoubleIota.tolerates(a.length(), b.length()));
        }else if(pattern.equals(LEN_NEQ)){
            return makeComp((a, b) -> !DoubleIota.tolerates(a.length(), b.length()));
        }else if(pattern.equals(IN_RANGE)){
            return makeRange((v, a, b, op) -> {
                double len = v.length();
                double min = Math.min(a, b);
                double max = Math.max(a, b);
                if(DoubleIota.tolerates(op, 0)){
                    return min < len && len < max;
                }else if(DoubleIota.tolerates(op, 1)){
                    return HextraUtils.lessEq(min, len) && len < max;
                }else if(DoubleIota.tolerates(op, 2)){
                    return min < len && HextraUtils.lessEq(len, max);
                }else if(DoubleIota.tolerates(op, 3)){
                    return HextraUtils.lessEq(min, len) && HextraUtils.lessEq(len, max);
                }else{
                    throw new InvalidOperatorException(op + " is not a valid op for Range Exaltation");
                }
            });
        }else if(pattern.equals(OUT_RANGE)){
            return makeRange((v, a, b, op) -> {
                double len = v.length();
                double min = Math.min(a, b);
                double max = Math.max(a, b);
                if(DoubleIota.tolerates(op, 0)){
                    return len < min || max < len;
                }else if(DoubleIota.tolerates(op, 1)){
                    return HextraUtils.lessEq(len, min) || max < len;
                }else if(DoubleIota.tolerates(op, 2)){
                    return len < min || HextraUtils.lessEq(max, len);
                }else if(DoubleIota.tolerates(op, 3)){
                    return HextraUtils.lessEq(len, min) || HextraUtils.lessEq(max, len);
                }else{
                    throw new InvalidOperatorException(op + " is not a valid op for Range Exaltation II");
                }
            });
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Vec3 Bool Arithmetic " + this);
        }
    }

    private OperatorBinary makeComp(Func2to1<Vec3d, Vec3d, Boolean> op){
        return new OperatorBinary(IotaMultiPredicate.all(IotaPredicate.ofType(VEC3)),
            (i, j) -> new BooleanIota(op.apply(Operator.downcast(i, VEC3).getVec3(), Operator.downcast(j, VEC3).getVec3()))
        );
    }

    private OperatorQuadary makeRange(Func4to1<Vec3d, Double, Double, Double, Boolean> op){
        return new OperatorQuadary(new QuadIotaPredicte(IotaPredicate.ofType(VEC3), IotaPredicate.ofType(DOUBLE), IotaPredicate.ofType(DOUBLE), IotaPredicate.ofType(DOUBLE)),
            (i, j, k, l) -> new BooleanIota(op.apply(Operator.downcast(i, VEC3).getVec3(), Operator.downcast(j, DOUBLE).getDouble(), Operator.downcast(k, DOUBLE).getDouble(), Operator.downcast(l, DOUBLE).getDouble()))
        );
    }
}

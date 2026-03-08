package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.OperatorQuadary;
import com.meepoffaith.hextrapats.util.HextraUtils;
import com.meepoffaith.hextrapats.util.QuadIotaPredicte;
import com.meepoffaith.hextrapats.util.generics.Func2to1;
import com.meepoffaith.hextrapats.util.generics.Func4to1;
import net.minecraft.text.Text;
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
        if(pattern.sigsEqual(GREATER)){
            return makeComp((a, b) -> a.length() > b.length());
        }else if(pattern.sigsEqual(LESS)){
            return makeComp((a, b) -> a.length() < b.length());
        }else if(pattern.sigsEqual(GREATER_EQ)){
            return makeComp((a, b) -> HextraUtils.greaterEq(a.length(), b.length()));
        }else if(pattern.sigsEqual(LESS_EQ)){
            return makeComp((a, b) -> HextraUtils.lessEq(a.length(), b.length()));
        }else if(pattern.sigsEqual(LEN_EQ)){
            return makeComp((a, b) -> DoubleIota.tolerates(a.length(), b.length()));
        }else if(pattern.sigsEqual(LEN_NEQ)){
            return makeComp((a, b) -> !DoubleIota.tolerates(a.length(), b.length()));
        }else if(pattern.sigsEqual(IN_RANGE)){
            return makeRange((v, a, b, opI) -> {
                double len = v.length();
                double min = Math.min(a, b);
                double max = Math.max(a, b);
                int op = HextraUtils.getInt(opI, 3);
                return switch(op){
                    case 0 -> min < len && len < max;
                    case 1 -> HextraUtils.lessEq(min, len) && len < max;
                    case 2 -> min < len && HextraUtils.lessEq(len, max);
                    case 3 -> HextraUtils.lessEq(min, len) && HextraUtils.lessEq(len, max);
                    default -> throw new MishapInvalidIota(opI, 0, Text.of("an integer from 0 to 3"));
                };
            });
        }else if(pattern.sigsEqual(OUT_RANGE)){
            return makeRange((v, a, b, opI) -> {
                double len = v.length();
                double min = Math.min(a, b);
                double max = Math.max(a, b);
                int op = HextraUtils.getInt(opI, 3);
                return switch(op){
                    case 0 -> len < min || max < len;
                    case 1 -> HextraUtils.lessEq(len, min) || max < len;
                    case 2 -> len < min || HextraUtils.lessEq(max, len);
                    case 3 -> HextraUtils.lessEq(len, min) || HextraUtils.lessEq(max, len);
                    default -> throw new MishapInvalidIota(opI, 0, Text.of("an integer from 0 to 3"));
                };
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

    private OperatorQuadary makeRange(Func4to1<Vec3d, Double, Double, Iota, Boolean> op){
        return new OperatorQuadary(new QuadIotaPredicte(IotaPredicate.ofType(VEC3), IotaPredicate.ofType(DOUBLE), IotaPredicate.ofType(DOUBLE), IotaPredicate.ofType(DOUBLE)),
            (i, j, k, l) -> new BooleanIota(op.apply(
                Operator.downcast(i, VEC3).getVec3(),
                Operator.downcast(j, DOUBLE).getDouble(),
                Operator.downcast(k, DOUBLE).getDouble(),
                l
            ))
        );
    }
}

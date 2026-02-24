package com.meepoffaith.hextra.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.casting.arithmetic.Vec3Arithmetic;
import com.meepoffaith.hextra.util.generics.Func2to1;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3;

public class Vec3BoolArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        GREATER,
        LESS,
        GREATER_EQ,
        LESS_EQ
    );

    @Override
    public String arithName(){
        return "hextra_bool_math";
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
            return makeComp((a, b) -> a.length() >= b.length());
        }else if(pattern.equals(LESS_EQ)){
            return makeComp((a, b) -> a.length() <= b.length());
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Vec3 Bool Arithmetic " + this);
        }
    }

    private OperatorBinary makeComp(Func2to1<Vec3d, Vec3d, Boolean> op){
        return new OperatorBinary(IotaMultiPredicate.all(IotaPredicate.ofType(VEC3)),
            (i, j) -> new BooleanIota(op.apply(Operator.downcast(i, VEC3).getVec3(), Operator.downcast(j, VEC3).getVec3()))
        );
    }
}

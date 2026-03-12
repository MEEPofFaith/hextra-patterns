package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorUnary;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.util.generics.Func1to1;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static com.meepoffaith.hextrapats.init.Arithmetics.*;

public class NumArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        INVERT,
        INCREMENT,
        DECREMENT
    );

    @Override
    public String arithName(){
        return "hextrapats_double_math";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){
        if(pattern.sigsEqual(INVERT)){
            return makeNumToNum(d -> -d);
        }if(pattern.sigsEqual(INCREMENT)){
            return makeNumToNum(d -> d + 1);
        }if(pattern.sigsEqual(DECREMENT)){
            return makeNumToNum(d -> d - 1);
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Vec3 Bool Arithmetic " + this);
        }
    }

    private OperatorUnary makeNumToNum(Func1to1<Double, Double> op){
        return new OperatorUnary(IotaMultiPredicate.all(IotaPredicate.ofType(DOUBLE)),
            i -> new DoubleIota(op.apply(Operator.downcast(i, DOUBLE).getDouble()))
        );
    }
}

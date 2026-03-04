package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.OperatorQuadary;
import com.meepoffaith.hextrapats.util.generics.Func4to1;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static com.meepoffaith.hextrapats.init.Arithmetics.IN_RANGE;
import static com.meepoffaith.hextrapats.init.Arithmetics.OUT_RANGE;

public class BoolArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        IN_RANGE,
        OUT_RANGE
    );

    @Override
    public String arithName(){
        return "hextrapats_bool_math";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){
        if(pattern.equals(IN_RANGE)){
            return makeRange((len, a, b, op) -> {
                double min = Math.min(a, b);
                double max = Math.max(a, b);
                if(op == 0){
                    return min < len && len < max;
                }else if(op == 1){
                    return min <= len && len < max;
                }else if(op == 2){
                    return min < len && len <= max;
                }else if(op == 3){
                    return min <= len && len <= max;
                }else{
                    throw new InvalidOperatorException(op + " is not a valid op for Range Gambit");
                }
            });
        }else if(pattern.equals(OUT_RANGE)){
            return makeRange((len, a, b, op) -> {
                double min = Math.min(a, b);
                double max = Math.max(a, b);
                if(op == 0){
                    return len < min || len > max;
                }else if(op == 1){
                    return len <= min || len > max;
                }else if(op == 2){
                    return len < min || len >= max;
                }else if(op == 3){
                    return len <= min || len >= max;
                }else{
                    throw new InvalidOperatorException(op + " is not a valid op for Range Gambit II");
                }
            });
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Bool Arithmetic " + this);
        }
    }

    private OperatorQuadary makeRange(Func4to1<Double, Double, Double, Double, Boolean> op){
        return new OperatorQuadary(IotaMultiPredicate.all(IotaPredicate.ofType(DOUBLE)),
            (i, j, k, l) -> new BooleanIota(op.apply(Operator.downcast(i, DOUBLE).getDouble(), Operator.downcast(j, DOUBLE).getDouble(), Operator.downcast(k, DOUBLE).getDouble(), Operator.downcast(l, DOUBLE).getDouble()))
        );
    }
}

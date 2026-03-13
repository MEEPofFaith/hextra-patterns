package com.meepoffaith.hextrapats.casting.arithmetic.operator;

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.util.generics.TrinaryOperator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OperatorTrinary extends OperatorBasic{
    public TrinaryOperator<Iota> inner;

    public OperatorTrinary(IotaMultiPredicate accepts, TrinaryOperator<Iota> inner) {
        super(3, accepts);
        this.inner = inner;
    }

    @Override
    public @NotNull Iterable<Iota> apply(Iterable<? extends Iota> iotas, @NotNull CastingEnvironment env) {
        var it = iotas.iterator();
        return List.of(inner.apply(it.next(), it.next(), it.next()));
    }
}

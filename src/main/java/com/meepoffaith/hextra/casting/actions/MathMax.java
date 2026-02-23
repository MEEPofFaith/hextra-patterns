package com.meepoffaith.hextra.casting.actions;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextra.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextra.casting.bases.HexIotaStack;

import java.util.List;

public class MathMax extends ConstMediaActionBase {
    public int argc = 2;
    public long mediaCost = 0L;

    @Override
    public List<Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        double A = stack.getDouble(0);
        double B = stack.getDouble(1);
        return List.of(new DoubleIota(Math.max(A, B)));
    }
}

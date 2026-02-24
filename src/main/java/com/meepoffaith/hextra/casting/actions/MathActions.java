package com.meepoffaith.hextra.casting.actions;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextra.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextra.casting.bases.HexIotaStack;

import java.util.List;

public class MathActions{
    public static class DegRad extends ConstMediaActionBase{
        public int argc = 1;

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            double x = stack.getDouble(0);
            return asActionResult(new DoubleIota(x * Math.PI / 180.0));
        }
    }

    public static class RadDeg extends ConstMediaActionBase{
        public int argc = 1;

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            double x = stack.getDouble(0);
            return asActionResult(new DoubleIota(x * 180.0 / Math.PI));
        }
    }
}

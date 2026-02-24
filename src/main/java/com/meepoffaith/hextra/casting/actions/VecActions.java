package com.meepoffaith.hextra.casting.actions;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import com.meepoffaith.hextra.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextra.casting.bases.HexIotaStack;
import net.minecraft.util.math.Vec3d;

import java.util.List;

//I feel like these actions are small enough to just bundle into one class.
public class VecActions{
    public static class VecOne extends ConstMediaActionBase{
        public int argc = 0;
        public long mediaCost = 0L;

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            return asActionResult(new Vec3Iota(new Vec3d(1, 1, 1)));
        }
    }

    public static class VecNegOne extends ConstMediaActionBase{
        public int argc = 0;
        public long mediaCost = 0L;

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            return asActionResult(new Vec3Iota(new Vec3d(-1, -1, -1)));
        }
    }
}

package com.meepoffaith.hextrapats.casting.actions.math;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class OpRandVec extends ConstMediaActionBase{
    public int argc = 0;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        double z = -1 + 2 * ctx.getWorld().random.nextDouble();
        double r = Math.sqrt(1 - z * z);
        double t = ctx.getWorld().random.nextDouble() * 2 * Math.PI;
        return asActionResult(new Vec3Iota(new Vec3d(r * Math.cos(t), r * Math.sin(t), z)));
    }
}

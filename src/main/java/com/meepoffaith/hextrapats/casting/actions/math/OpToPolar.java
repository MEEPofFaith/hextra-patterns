package com.meepoffaith.hextrapats.casting.actions.math;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class OpToPolar extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        Vec3d v = stack.getVec3(0);

        double yaw = -Math.atan2(v.x, v.z);
        double pitch = -Math.asin(v.y);

        return List.of(new DoubleIota(pitch), new DoubleIota(yaw));
    }
}

package com.meepoffaith.hextrapats.casting.actions.vecmath;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.util.MathUtils;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class OpVecAngleDist extends ConstMediaActionBase{
    public int argc = 2;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        Vec3d a = stack.getVec3(0);
        Vec3d b = stack.getVec3(1);

        return asActionResult(new DoubleIota(MathUtils.vecAngleDist(a, b)));
    }
}

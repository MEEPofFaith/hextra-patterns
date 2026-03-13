package com.meepoffaith.hextrapats.casting.actions.vecmath;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.util.MathUtils;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class OpRotateTowards extends ConstMediaActionBase{
    public int argc = 3;
    public long mediaCost = 0L;

    //Once again I am calling upon the guidance of https://stackoverflow.com/questions/22099490/calculate-vector-after-rotating-it-towards-another-by-angle-θ-in-3d-space
    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        Vec3d from = stack.getVec3(0);
        Vec3d to = stack.getVec3(1);
        double theta = stack.getDouble(2);

        if(theta >= MathUtils.vecAngleDist(from, to)){
            return asActionResult(new Vec3Iota(to.multiply(from.length() / to.length())));
        }

        Vec3d fromN = from.normalize();
        Vec3d toN = to.normalize();

        Vec3d cross = fromN.crossProduct(toN).crossProduct(fromN).normalize();
        Vec3d next = fromN.multiply(Math.cos(theta)).add(cross.multiply(Math.sin(theta)));

        return asActionResult(new Vec3Iota(next.multiply(from.length())));
    }
}

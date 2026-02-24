package com.meepoffaith.hextra.casting.handlers;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextra.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextra.casting.bases.HexIotaStack;
import com.meepoffaith.hextra.init.SpecialHandlers;
import com.meepoffaith.hextra.util.HextraUtils;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZVectorLiteral implements SpecialHandler{
    double z;

    public ZVectorLiteral(double z){
        this.z = z;
    }

    @Override
    public Action act(){
        return new InnerAction(z);
    }

    @Override
    public Text getName(){
        return HextraUtils.specialHandlerLang(
            SpecialHandlers.SCALED_VEC_Z,
            Action.Companion.getDOUBLE_FORMATTER().format(z)
        );
    }

    public static class InnerAction extends ConstMediaActionBase{
        public int argc = 0;
        public long mediaCost = 0L;
        double z;

        public InnerAction(double z){
            this.z = z;
        }

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            return asActionResult(new Vec3Iota(new Vec3d(0, 0, z)));
        }
    }

    public static class ZVectorLiteralFactory implements Factory<ZVectorLiteral> {
        @Override
        public @Nullable ZVectorLiteral tryMatch(HexPattern pattern, CastingEnvironment env){
            String sig = pattern.anglesSignature();
            if(sig.startsWith("deqqqqqaw") || sig.startsWith("dqeeeeedw")){
                double val = HextraUtils.numericalReflection(sig.substring(9)) *
                    (sig.startsWith("dqeeeeedw") ? -1.0 : 1.0);
                return new ZVectorLiteral(val);
            }else{
                return null;
            }
        }
    }
}

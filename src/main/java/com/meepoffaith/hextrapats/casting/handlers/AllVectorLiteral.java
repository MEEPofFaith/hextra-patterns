package com.meepoffaith.hextrapats.casting.handlers;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.init.SpecialHandlers;
import com.meepoffaith.hextrapats.util.HextraUtils;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AllVectorLiteral implements SpecialHandler{
    double x;

    public AllVectorLiteral(double x){
        this.x = x;
    }

    @Override
    public Action act(){
        return new InnerAction(x);
    }

    @Override
    public Text getName(){
        String val = Action.Companion.getDOUBLE_FORMATTER().format(x);
        return HextraUtils.specialHandlerLang(SpecialHandlers.SCALED_VEC_ALL, val, val, val);
    }

    public static class InnerAction extends ConstMediaActionBase{
        public int argc = 0;
        public long mediaCost = 0L;
        double x;

        public InnerAction(double x){
            this.x = x;
        }

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            return asActionResult(new Vec3Iota(new Vec3d(x, x, x)));
        }
    }

    public static class AllVectorLiteralFactory implements Factory<AllVectorLiteral> {
        @Override
        public @Nullable AllVectorLiteral tryMatch(HexPattern pattern, CastingEnvironment env){
            String sig = pattern.anglesSignature();
            if(sig.startsWith("qeqqqqqaw") || sig.startsWith("qqeeeeedw")){
                double val = HextraUtils.numericalReflection(sig.substring(9)) *
                    (sig.startsWith("qqeeeeedw") ? -1.0 : 1.0);
                return new AllVectorLiteral(val);
            }else{
                return null;
            }
        }
    }
}
